@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundReservationService reservationService;
    private final RefundCompletionService completionService;
    private final PaymentGatewayClient gateway;

    public RefundResponse refund(
            Long paymentId,
            BigDecimal amount,
            String reason,
            String idempotencyKey) {

        Refund refund =
            reservationService.reserve(
                paymentId,
                amount,
                reason,
                idempotencyKey
            );

        if (refund.getStatus() == RefundStatus.COMPLETED) {
            return RefundResponse.from(refund);
        }

        GatewayRefundResult gatewayResult;

        try {

            gatewayResult =
                gateway.refund(
                    refund.getGatewayPaymentId(),
                    refund.getAmount(),
                    refund.getIdempotencyKey()
                );

        } catch (PermanentGatewayException e) {

            completionService.markFailed(
                refund.getId(),
                e.getCode()
            );

            throw e;

        } catch (RetryableGatewayException e) {

            completionService.markRetryableFailure(
                refund.getId(),
                e.getCode()
            );

            throw e;
        }

        Refund completed =
            completionService.markCompleted(
                refund.getId(),
                gatewayResult.externalRefundId()
            );

        return RefundResponse.from(completed);
    }
}

@Service
@RequiredArgsConstructor
public class RefundService {

    private final TransactionTemplate tx;
    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;
    private final PaymentGatewayClient gateway;

    public Refund refund(...) {

        Refund refund = tx.execute(status -> {
            return reserveRefund(...);
        });

        GatewayRefundResult gatewayResult =
            gateway.refund(
                refund.getGatewayPaymentId(),
                refund.getAmount(),
                refund.getIdempotencyKey()
            );

        return tx.execute(status -> {
            return completeRefund(
                refund.getId(),
                gatewayResult.externalRefundId()
            );
        });
    }
}