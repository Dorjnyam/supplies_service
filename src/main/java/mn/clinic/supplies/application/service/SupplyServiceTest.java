// package mn.clinic.supplies.application.service;

// import mn.clinic.supplies.application.port.out.SupplyRepositoryPort;
// import mn.clinic.supplies.adapter.out.messaging.KafkaMessageProducer;
// import mn.clinic.supplies.domain.model.Supply;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.time.LocalDate;

// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// public class SupplyServiceTest {

//     @Mock
//     private SupplyRepositoryPort repositoryPort;

//     @Mock
//     private KafkaProducer kafkaProducer;

//     @InjectMocks
//     private SupplyService supplyService;

//     @BeforeEach
//     void setUp() {
//         // This is where setup is done for each test case
//     }

//     @Test
//     void addSupply_shouldSaveAndPublishMessage() {
//         // Given
//         Supply supply = new Supply("Test Syringe", 100, LocalDate.now().plusMonths(6), "TestSupplier");

//         // When
//         supplyService.addSupply(supply);

//         // Then
//         verify(repositoryPort, times(1)).save(supply); // Verifying the save method is called
//         verify(kafkaProducer, times(1)).sendMessage(eq("new-supplies"), anyString()); // Verifying Kafka message is sent
//     }

//     @Test
//     void listSupplies_shouldReturnListOfSupplies() {
//         // Given
//         Supply supply = new Supply("Test Syringe", 100, LocalDate.now().plusMonths(6), "TestSupplier");
//         when(repositoryPort.findAll()).thenReturn(List.of(supply));

//         // When
//         List<Supply> result = supplyService.listSupplies();

//         // Then
//         assertNotNull(result);
//         assertEquals(1, result.size());
//         assertEquals("Test Syringe", result.get(0).getName());
//     }
// }
