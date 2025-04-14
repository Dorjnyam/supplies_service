package mn.clinic.supplies.application.port.out;

public interface EventPublisher {
    void publishEvent(String event);
}
