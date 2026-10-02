@Service
@RequiredArgsConstructor
public class RefundCommandService {

    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;
    private final Clock clock;

    @Transactional
    public Refund createRefund(
            Long paymentId,
            BigDecimal amount,
            String reason,
            String idempotencyKey) {

        validateInput(amount, idempotencyKey);

        // Idempotency at API/business level
        Optional<Refund> existing =
            refundRepository.findByIdempotencyKey(
                idempotencyKey
            );

        if (existing.isPresent()) {
            return existing.get();
        }

        Payment payment =
            paymentRepository.findByIdForUpdate(paymentId)
                .orElseThrow(
                    () -> new PaymentNotFoundException(paymentId)
                );

        validatePaymentState(payment);

        BigDecimal refundable =
            payment.getAmount()
                .subtract(payment.getRefundedAmount());

        if (amount.compareTo(refundable) > 0) {
            throw new RefundNotAllowedException(
                "Refund amount exceeds refundable balance"
            );
        }

        /*
         * Reserve the amount immediately.
         *
         * Important:
         * this means refundedAmount here behaves as
         * "reserved/refunded total", not only completed refunds.
         *
         * In a more sophisticated model, I'd separate:
         *
         * reservedRefundAmount
         * completedRefundAmount
         */
        BigDecimal newRefundedAmount =
            payment.getRefundedAmount()
                .add(amount);

        payment.setRefundedAmount(newRefundedAmount);

        if (newRefundedAmount.compareTo(payment.getAmount()) == 0) {
            payment.setStatus(PaymentStatus.REFUNDED);
        } else {
            payment.setStatus(PaymentStatus.PARTIALLY_REFUNDED);
        }

        Refund refund = new Refund();

        refund.setPaymentId(paymentId);
        refund.setAmount(amount);
        refund.setReason(sanitizeReason(reason));
        refund.setIdempotencyKey(idempotencyKey);
        refund.setStatus(RefundStatus.PENDING);
        refund.setCreatedAt(clock.instant());

        refundRepository.save(refund);
        paymentRepository.save(payment);

        return refund;
    }

    private void validateInput(
            BigDecimal amount,
            String idempotencyKey) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                "Refund amount must be greater than zero"
            );
        }

        if (idempotencyKey == null ||
                idempotencyKey.isBlank()) {

            throw new IllegalArgumentException(
                "Idempotency key is required"
            );
        }
    }

    private void validatePaymentState(Payment payment) {

        if (payment.getStatus() != PaymentStatus.CAPTURED
                &&
            payment.getStatus()
                != PaymentStatus.PARTIALLY_REFUNDED) {

            throw new RefundNotAllowedException(
                "Payment is not refundable"
            );
        }
    }

    private String sanitizeReason(String reason) {

        if (reason == null) {
            return null;
        }

        String value = reason.trim();

        if (value.length() > 500) {
            throw new IllegalArgumentException(
                "Refund reason is too long"
            );
        }

        return value;
    }
}