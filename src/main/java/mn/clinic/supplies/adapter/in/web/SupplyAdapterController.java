package mn.clinic.supplies.adapter.in.web;

import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
import mn.clinic.supplies.domain.model.Supply;
import mn.clinic.supplies.adapter.out.kafka.KafkaProducer; // Import KafkaProducer
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;  // Changed to jakarta.validation
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyAdapterController {

    private final ManageSupplyUseCase supplyUseCase;
    private final KafkaProducer kafkaProducer;

    // Constructor to inject dependencies
    public SupplyAdapterController(ManageSupplyUseCase supplyUseCase, KafkaProducer kafkaProducer) {
        this.supplyUseCase = supplyUseCase;
        this.kafkaProducer = kafkaProducer;
    }

    // Endpoint to add a new supply
    @PostMapping
    public ResponseEntity<Void> addSupply(@RequestBody @Valid Supply supply, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Invalid input
        }

        // Save the supply through the use case
        supplyUseCase.addSupply(supply);

        // Send a message to Kafka about the new supply
        kafkaProducer.sendMessage("New supply added: " + supply.getName());

        return new ResponseEntity<>(HttpStatus.CREATED);  // Success
    }

    // Endpoint to fetch all supplies with optional filtering and pagination
    @GetMapping
    public ResponseEntity<List<Supply>> getAllSupplies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) String expiryDate,
            @RequestParam int page,
            @RequestParam int size) {
        
        // Creating pageable object for pagination
        Pageable pageable = PageRequest.of(page, size);
        
        // Fetch filtered supplies with pagination
        Page<Supply> supplies = supplyUseCase.listSuppliesWithPagination(pageable, name, supplier, expiryDate);

        // Send a message to Kafka about fetching supplies
        kafkaProducer.sendMessage("Fetched supplies list. Page: " + page + " Size: " + size);

        return ResponseEntity.ok(supplies.getContent());
    }

    // Endpoint to update an existing supply
    @PutMapping("/{id}")
    public ResponseEntity<Supply> updateSupply(@PathVariable Long id, @RequestBody @Valid Supply updatedSupply, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Invalid input
        }

        // Fetch the existing supply by ID
        Supply existingSupply = supplyUseCase.findSupplyById(id);
        if (existingSupply == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Supply not found
        }

        // Update the supply fields
        existingSupply.setName(updatedSupply.getName());
        existingSupply.setQuantity(updatedSupply.getQuantity());
        existingSupply.setExpiryDate(updatedSupply.getExpiryDate());
        existingSupply.setSupplier(updatedSupply.getSupplier());

        // Save the updated supply
        supplyUseCase.updateSupply(existingSupply);

        // Send a message to Kafka about updating the supply
        kafkaProducer.sendMessage("Updated supply with ID: " + id + ", Name: " + existingSupply.getName());

        return ResponseEntity.ok(existingSupply);  // Return the updated supply
    }

    // Endpoint to delete a supply
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        boolean deleted = supplyUseCase.deleteSupply(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Supply not found
        }

        // Send a message to Kafka about deleting the supply
        kafkaProducer.sendMessage("Deleted supply with ID: " + id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Successfully deleted
    }

    // Endpoint to search supplies based on a query
    @GetMapping("/search")
    public List<Supply> searchSupplies(@RequestParam("query") String query) {
        // Perform the search operation
        List<Supply> supplies = supplyUseCase.searchSupplies(query);

        // Send a message to Kafka about the search
        kafkaProducer.sendMessage("Searched for supplies with query: " + query);

        return supplies;
    }
}
