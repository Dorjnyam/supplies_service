package mn.clinic.supplies.adapter.in.web;

import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
import mn.clinic.supplies.domain.model.Supply;
import mn.clinic.supplies.adapter.out.kafka.KafkaProducer; // Import KafkaProducer
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    private final ManageSupplyUseCase supplyUseCase;
    private final KafkaProducer kafkaProducer; // Inject KafkaProducer

    // Constructor to inject dependencies
    public SupplyController(ManageSupplyUseCase supplyUseCase, KafkaProducer kafkaProducer) {
        this.supplyUseCase = supplyUseCase;
        this.kafkaProducer = kafkaProducer;
    }

    // Endpoint to add a new supply
    @PostMapping
    public ResponseEntity<Void> addSupply(@RequestBody Supply supply) {
        // Save the supply through the use case
        supplyUseCase.addSupply(supply);
        
        // Send a message to Kafka about the new supply
        kafkaProducer.sendMessage("New supply added: " + supply.getName());
        
        // Return a 201 status to indicate the resource was created successfully
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Endpoint to fetch all supplies
    @GetMapping
    public ResponseEntity<List<Supply>> getAllSupplies() {
        return ResponseEntity.ok(supplyUseCase.listSupplies());
    }
}
