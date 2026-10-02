public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select p
        from Payment p
        where p.id = :id
    """)
    Optional<Payment> findByIdForUpdate(
            @Param("id") Long id
    );
}