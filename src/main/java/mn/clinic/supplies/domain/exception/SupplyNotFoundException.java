package mn.clinic.supplies.domain.exception;

public class SupplyNotFoundException extends RuntimeException {
    public SupplyNotFoundException(String message) {
        super(message);
    }
}
