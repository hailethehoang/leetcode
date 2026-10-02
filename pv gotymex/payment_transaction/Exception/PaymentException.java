public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(Long id) {
        super("Payment not found: " + id);
    }
}

public class RefundNotFoundException extends RuntimeException {

    public RefundNotFoundException(Long id) {
        super("Refund not found: " + id);
    }
}

public class RefundNotAllowedException extends RuntimeException {

    public RefundNotAllowedException(String message) {
        super(message);
    }
}

