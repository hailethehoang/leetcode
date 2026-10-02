@Service
@RequiredArgsConstructor
public class RefundFinalizationService {

    private final RefundRepository refundRepository;

    @Transactional
    public void markCompleted(
            Long refundId,
            String externalRefundId) {

        Refund refund =
            refundRepository.findById(refundId)
                .orElseThrow(
                    () -> new RefundNotFoundException(refundId)
                );

        if (refund.getStatus() == RefundStatus.COMPLETED) {
            return;
        }

        refund.setStatus(RefundStatus.COMPLETED);
        refund.setExternalId(externalRefundId);
        refund.setCompletedAt(Instant.now());

        refundRepository.save(refund);
    }

    @Transactional
    public void markFailed(
            Long refundId,
            String failureCode) {

        Refund refund =
            refundRepository.findById(refundId)
                .orElseThrow(
                    () -> new RefundNotFoundException(refundId)
                );

        refund.setStatus(RefundStatus.FAILED);
        refund.setFailureCode(failureCode);

        refundRepository.save(refund);
    }
}