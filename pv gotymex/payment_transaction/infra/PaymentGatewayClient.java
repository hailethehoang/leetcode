public interface PaymentGatewayClient {

    GatewayRefundResult refund(
        String gatewayPaymentId,
        BigDecimal amount,
        String idempotencyKey
    );
}
