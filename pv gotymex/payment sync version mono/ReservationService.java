@Service
@RequiredArgsConstructor
public class RefundReservationService {

    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;

    @Transactional
    public Refund reserve(
            Long paymentId,
            BigDecimal amount,
            String reason,
            String idempotencyKey) {

        Optional<Refund> existing =
            refundRepository.findByIdempotencyKey(idempotencyKey);

        if (existing.isPresent()) {
            return existing.get();
        }

        Payment payment =
            paymentRepository.findByIdForUpdate(paymentId)
                .orElseThrow(
                    () -> new PaymentNotFoundException(paymentId)
                );

        BigDecimal available =
            payment.getAmount()
                .subtract(payment.getRefundedAmount())
                .subtract(payment.getReservedRefundAmount());

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RefundNotAllowedException(
                "Amount must be positive"
            );
        }

        if (amount.compareTo(available) > 0) {
            throw new RefundNotAllowedException(
                "Insufficient refundable amount"
            );
        }

        payment.setReservedRefundAmount(
            payment.getReservedRefundAmount().add(amount)
        );

        Refund refund = new Refund();
        refund.setPaymentId(paymentId);
        refund.setAmount(amount);
        refund.setReason(reason);
        refund.setIdempotencyKey(idempotencyKey);
        refund.setStatus(RefundStatus.PENDING);

        return refundRepository.save(refund);
    }
}
