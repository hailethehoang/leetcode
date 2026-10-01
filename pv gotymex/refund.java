package com.example.payments;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentRefundService {

    private final SimpleDateFormat dateFmt =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      // Not a thread-field - should be careful with race-condition while use by threads
      // DateTimeFormatter is immutable and thread-safe

    @Autowired
    private PaymentRepository paymentRepository;
    // cleaner to use construction DI instead of field injection

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private PaymentGatewayClient gateway;


    public Refund refundPayment(
            Long paymentId,
            double amount,
            String reason) {

        Payment payment =
            paymentRepository.findById(paymentId).orElse(null);
        // apply persimessive lock here so another request cannot proccess at same time 

        if (payment == null) {
            log.warn("Payment not found: " + paymentId);
            return null;
        }
        // should Throw business error here instead of return null. PaymentNotFoundException

        // should have more validation like 
        // payment.Amount > 0 

        if (payment.getStatus() == "CAPTURED") {
            // use equals lile !"CAPTURED".equals 
            // and changed to use a static final constant is better for refactoring

            // double should not present money, better to use BigDecimal here
            double refundable =
                payment.getAmount()
                - payment.getRefundedAmount();

            if (amount > refundable) {
                throw new RuntimeException(
                    "Refund amount exceeds refundable balance"
                );
                // should declare a custom exception here other class handle base on error type
            }

        } else {
            throw new RuntimeException(
                "Payment is not refundable"
            );
            // should declare a custom exception here
        }


        Refund refund = new Refund();

        refund.setPaymentId(paymentId);
        refund.setAmount(amount);
        refund.setReason(reason);

        refund.setCreatedAt(
            dateFmt.format(new Date())
        );
        // should not use the field of this call for race condition
        // refund.setCreatedAt(Instant.now());
        

        refund = refundRepository.save(refund);
        // Event if payment not found, still create, save and proccess refund - then call refund API
        // instead we should stop and return business error


        int attempts = 0;

        // instead of manual api call, can use Resilient4J to retry call with circut breaker, jitter time for retries
        // also we should add inpempotency key contruct from paymentId+ uuid => handle duplicate request
        // Purpose : API can hanlde duplication
        // better: should check in our DB first before send as a OUTBOX pattern
        // retries have no backoff
        while (attempts < 5) {

            try {

                String externalId =
                    gateway.submitRefund(
                        payment.getGatewayId(),
                        amount
                    );

                refund.setExternalId(externalId);
                refund.setStatus("SUBMITTED");
                // use Constant instead

                // refund is not saved if success
                // I see no transaction apply, we should break down to smaller function and apply transaction
                // at each step

                break;

            } catch (Exception e) {

                attempts++;

                // log the error to monitor
                // this silence error is dangerous
                // Dead letter queue should be use after failure to process
            }
        }
        // A stronger architecture may move the gateway work to asynchronous processing.
        // Not always mandatory, but worth mentioning under scalability/resilience.


        // If the previous step not success we should not process anything down here
        // all retries can fail, but code still records refund

        payment.setRefundedAmount(
            payment.getRefundedAmount() + amount
        );

        if (
            payment.getRefundedAmount()
                == payment.getAmount()
        ) {

            payment.setStatus("REFUNDED");
        }

        paymentRepository.save(payment);


        log.info(
            "Refund issued: paymentId="
            + paymentId
            + ", amount="
            + amount
            + ", card="
            + payment.getCardNumber()
            + ", cvv="
            + payment.getCvv()
        );
        // should not log the sensituve infomation here


        return refund;
    }


    public Refund getRefund(Long id) {

        return refundRepository
            .findById(id)
            .get();
    }
}

// better solution
// POST /refund
//        |
//        v
// validate + reserve refund amount
//        |
//        v
// create Refund
// status = PENDING
// idempotencyKey = UUID/client key
//        |
//     COMMIT DB
//        |
//        v
// call gateway
//        |
//        +---- success --------+
//        |                     |
//        v                     |
// status = SUBMITTED           |
// externalId = xxx             |
// update payment totals        |
//                              |
//        +---- failure --------+
//                |
//                v
//          RETRY / FAILED /
//          UNKNOWN

// REQUESTED
// PROCESSING
// SUBMITTED
// COMPLETED
// FAILED
// UNKNOWN

// DB transaction
//     ↓
// Refund(PENDING)
// Outbox event
//     ↓
// commit
//     ↓
// worker
//     ↓
// payment gateway