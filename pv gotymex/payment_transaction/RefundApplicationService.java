@Service
@RequiredArgsConstructor
public class RefundApplicationService {

    private final RefundCommandService commandService;
    private final RefundQueuePublisher queuePublisher;

    public RefundResponse refund(
            Long paymentId,
            BigDecimal amount,
            String reason,
            String idempotencyKey) {

        Refund refund =
            commandService.createRefund(
                paymentId,
                amount,
                reason,
                idempotencyKey
            );

        if (refund.getStatus() == RefundStatus.PENDING) {
            queuePublisher.publish(refund.getId());
        }

        return toResponse(refund);
    }

    private RefundResponse toResponse(Refund refund) {

        return new RefundResponse(
            refund.getId(),
            refund.getPaymentId(),
            refund.getAmount(),
            refund.getStatus(),
            refund.getExternalId(),
            refund.getCreatedAt()
        );
    }
}