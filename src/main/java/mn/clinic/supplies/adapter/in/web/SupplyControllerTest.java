// package mn.clinic.supplies.adapter.in.web;

// import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
// import mn.clinic.supplies.domain.model.Supply;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

// @ExtendWith(MockitoExtension.class)
// public class SupplyControllerTest {

//     @Mock
//     private ManageSupplyUseCase supplyUseCase;

//     @InjectMocks
//     private SupplyController supplyController;

//     private MockMvc mockMvc;

//     @BeforeEach
//     void setUp() {
//         mockMvc = MockMvcBuilders.standaloneSetup(supplyController).build();
//     }

//     @Test
//     void addSupply_shouldReturnCreated() throws Exception {
//         // Given
//         Supply supply = new Supply("Test Syringe", 100, LocalDate.now().plusMonths(6), "TestSupplier");

//         // When
//         when(supplyUseCase.addSupply(supply)).thenReturn(true);

//         // Then
//         mockMvc.perform(post("/supplies")
//                 .contentType("application/json")
//                 .content("{\"name\": \"Test Syringe\", \"quantity\": 100, \"expiryDate\": \"2025-12-31\", \"supplier\": \"TestSupplier\"}"))
//                 .andExpect(status().isCreated());
//     }
// }
