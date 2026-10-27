package cat.itacademy.s04.t02.n01.fruitapimysql;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SupplierIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    @DisplayName("PUT /suppliers should modify supplier in the database and return expected HTTP response")
    void updateSupplier_shouldModifySupplierInDatabaseAndReturnExpectedHttpResponse() throws Exception {

        Supplier initialSupplier = supplierRepository.save(new Supplier("Refruit", "Belgium"));

        Long supplierId = initialSupplier.getId();

        SupplierRequestDto supplierRequest = new SupplierRequestDto("Green Field", "Hungary");
        String jsonPayload = objectMapper.writeValueAsString(supplierRequest);

        mockMvc.perform(put("/suppliers/{id}", supplierId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(supplierId))
                .andExpect(jsonPath("$.name").value("Green Field"))
                .andExpect(jsonPath("$.country").value("Hungary"));

        Optional<Supplier> allegedSupplier = supplierRepository.findById(supplierId);

        if(allegedSupplier.isEmpty()) {
            throw new Exception("Error");
        }

        Supplier supplierFromMysql = allegedSupplier.get();

        assertEquals("Green Field", supplierFromMysql.getName());
        assertEquals("Hungary", supplierFromMysql.getCountry());
    }

}
