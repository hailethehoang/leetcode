

@Entity
@Table(name = "payment")
@Getter
@Setter
public class Payment {

    public enum PaymentStatus {
        CAPTURED,
        PARTIALLY_REFUNDED,
        REFUNDED
    }

    @Id
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal refundedAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private String gatewayId;

    @Version
    private Long version;
}

