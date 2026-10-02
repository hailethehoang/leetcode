@Service
@RequiredArgsConstructor
@Slf4j
public class RefundProcessor {

    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayClient gateway;
    private final RefundFinalizationService finalizationService;

    public void process(Long refundId) {

        Refund refund = refundRepository.findById(refundId)
            .orElseThrow(
                () -> new RefundNotFoundException(refundId)
            );

        if (refund.getStatus() == RefundStatus.COMPLETED) {
            return;
        }

        Payment payment =
            paymentRepository.findById(refund.getPaymentId())
                .orElseThrow(
                    () -> new PaymentNotFoundException(
                        refund.getPaymentId()
                    )
                );

        try {

            GatewayRefundResult result =
                gateway.refund(
                    payment.getGatewayId(),
                    refund.getAmount(),
                    refund.getIdempotencyKey()
                );

            finalizationService.markCompleted(
                refundId,
                result.externalRefundId()
            );

        } catch (RetryableGatewayException e) {

            log.warn(
                "Refund gateway temporarily unavailable, refundId={}",
                refundId
            );

            throw e;

        } catch (PermanentGatewayException e) {

            finalizationService.markFailed(
                refundId,
                e.getCode()
            );

            log.warn(
                "Refund permanently rejected, refundId={}, code={}",
                refundId,
                e.getCode()
            );
        }
    }
}