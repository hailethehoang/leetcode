@Service
@RequiredArgsConstructor
public class RefundCompletionService {

    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public Refund markCompleted(
            Long refundId,
            String externalRefundId) {

        Refund refund =
            refundRepository.findById(refundId)
                .orElseThrow();

        if (refund.getStatus() == RefundStatus.COMPLETED) {
            return refund;
        }

        Payment payment =
            paymentRepository.findByIdForUpdate(
                refund.getPaymentId()
            ).orElseThrow();

        BigDecimal amount = refund.getAmount();

        payment.setReservedRefundAmount(
            payment.getReservedRefundAmount()
                .subtract(amount)
        );

        payment.setRefundedAmount(
            payment.getRefundedAmount()
                .add(amount)
        );

        refund.setExternalId(externalRefundId);
        refund.setStatus(RefundStatus.COMPLETED);

        return refund;
    }

    @Transactional
    public void markFailed(
            Long refundId,
            String failureCode) {

        Refund refund =
            refundRepository.findById(refundId)
                .orElseThrow();

        if (refund.getStatus() == RefundStatus.FAILED) {
            return;
        }

        Payment payment =
            paymentRepository.findByIdForUpdate(
                refund.getPaymentId()
            ).orElseThrow();

        payment.setReservedRefundAmount(
            payment.getReservedRefundAmount()
                .subtract(refund.getAmount())
        );

        refund.setStatus(RefundStatus.FAILED);
        refund.setFailureCode(failureCode);
    }
}