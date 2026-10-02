public record RefundRequest(
    BigDecimal amount,
    String reason
) {}

public record RefundResponse(
    Long id,
    Long paymentId,
    BigDecimal amount,
    RefundStatus status,
    String externalId,
    Instant createdAt
) {}