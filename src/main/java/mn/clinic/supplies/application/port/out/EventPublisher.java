package mn.clinic.supplies.application.port.out;

public interface EventPublisher {
    void publishEvent(String event);         // For simple string events
    void publishDomainEvent(Object event);   // For structured domain events
}
