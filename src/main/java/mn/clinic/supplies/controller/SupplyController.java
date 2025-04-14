package mn.clinic.supplies.controller;

import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
import mn.clinic.supplies.domain.model.Supply;
import mn.clinic.supplies.adapter.out.kafka.KafkaProducer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

    private final ManageSupplyUseCase supplyUseCase;
    private final KafkaProducer kafkaProducer;

    // Constructor to inject dependencies
    public SupplyController(ManageSupplyUseCase supplyUseCase, KafkaProducer kafkaProducer) {
        this.supplyUseCase = supplyUseCase;
        this.kafkaProducer = kafkaProducer;
    }

    // Endpoint to add a new supply
    @PostMapping
    public ResponseEntity<Void> addSupply(@RequestBody @Valid Supply supply) {
        // Save the supply through the use case
        supplyUseCase.addSupply(supply);

        // Send a message to Kafka about the new supply
        kafkaProducer.sendMessage("New supply added: " + supply.getName());

        // Return a 201 status to indicate the resource was created successfully
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Endpoint to fetch all supplies with pagination and optional filters
    @GetMapping
    public ResponseEntity<List<Supply>> getAllSupplies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) String expiryDate,
            @RequestParam(defaultValue = "0") int page, // Default value for page
            @RequestParam(defaultValue = "10") int size) { // Default value for size

        Pageable pageable = PageRequest.of(page, size); // Create Pageable object for pagination
        Page<Supply> supplies = supplyUseCase.listSuppliesWithPagination(pageable, name, supplier, expiryDate);

        return ResponseEntity.ok(supplies.getContent()); // Return the list of supplies
    }

    // Endpoint to update an existing supply
    @PutMapping("/{id}")
    public ResponseEntity<Supply> updateSupply(@PathVariable Long id, @RequestBody @Valid Supply updatedSupply) {
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

        return ResponseEntity.ok(existingSupply);  // Return the updated supply
    }

    // Endpoint to delete a supply
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        boolean deleted = supplyUseCase.deleteSupply(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Supply not found
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Successfully deleted
    }

    // Endpoint to search supplies by name or supplier
    @GetMapping("/search")
    public ResponseEntity<List<Supply>> searchSupplies(@RequestParam String query) {
        List<Supply> results = supplyUseCase.searchSupplies(query);
        return ResponseEntity.ok(results);
    }

}
