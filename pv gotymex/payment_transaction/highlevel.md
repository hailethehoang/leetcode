HTTP request
   ↓
createRefundRequest()
   ↓
DB transaction:
  lock payment
  validate
  reserve refundable amount
  create Refund(PENDING)
  commit
   ↓
processRefund()
   ↓
call gateway with idempotency key
   ↓
DB transaction:
  success → Refund(SUBMITTED/COMPLETED)
  failure → Refund(FAILED/RETRYABLE)



                    POST /refund
                       │
                       │ Idempotency-Key
                       ▼
             RefundApplicationService
                       │
                       ▼
              BEGIN TRANSACTION
                       │
                       ▼
               lock Payment row
                       │
          ┌────────────┴────────────┐
          │                         │
      validate                   reject
          │
          ▼
 reserve refund amount
          │
          ▼
 create Refund(PENDING)
          │
          ▼
 create OutboxEvent
          │
                       ▼
                    COMMIT
                       │
                       ▼
                return 202
                       │
                       ▼
                 Outbox worker
                       │
                       ▼
                Kafka / Queue
                       │
                       ▼
               RefundProcessor
                       │
                       ▼
             Payment Gateway
          same idempotency key
                       │
              ┌────────┴────────┐
              │                 │
           success           failure
              │                 │
              ▼                 ▼
          COMPLETED       retry transient
                               │
                         permanent failure
                               │
                               ▼
                             FAILED
                         release reservation