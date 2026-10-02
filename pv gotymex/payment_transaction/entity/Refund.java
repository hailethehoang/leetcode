@Entity
@Table(
    name = "refund",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_refund_idempotency_key",
            columnNames = "idempotency_key"
        )
    }
)
@Getter
@Setter
public class Refund {

    public enum RefundStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long paymentId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(length = 500)
    private String reason;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant completedAt;

    private String failureCode;
}

