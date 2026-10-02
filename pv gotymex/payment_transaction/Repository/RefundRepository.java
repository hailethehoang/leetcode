public interface RefundRepository
        extends JpaRepository<Refund, Long> {

    Optional<Refund> findByIdempotencyKey(
        String idempotencyKey
    );
}