@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class RefundController {

    private final RefundApplicationService refundService;

    @PostMapping("/{paymentId}/refunds")
    public ResponseEntity<RefundResponse> refund(
            @PathVariable Long paymentId,

            @RequestHeader("Idempotency-Key")
            String idempotencyKey,

            @RequestBody RefundRequest request) {

        RefundResponse response =
            refundService.refund(
                paymentId,
                request.amount(),
                request.reason(),
                idempotencyKey
            );

        return ResponseEntity.accepted()
            .body(response);
    }
}