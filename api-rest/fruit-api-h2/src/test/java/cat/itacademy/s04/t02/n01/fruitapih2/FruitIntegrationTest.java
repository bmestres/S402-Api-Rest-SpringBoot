package cat.itacademy.s04.t02.n01.fruitapih2;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapih2.repositories.FruitRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FruitIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FruitRepository fruitRepository;

    @Test
    @DisplayName("POST /fruits should save a new fruit in the H2 database and return expected HTTP response")
    void createFruit_shouldSaveInDatabaseAndReturnExpectedResponse() throws Exception {

        assertEquals(0, fruitRepository.count());

        FruitRequestDto testRequest = new FruitRequestDto("Strawberry", 1);
        String jsonPayload = objectMapper.writeValueAsString(testRequest);

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Strawberry"))
                .andExpect(jsonPath("$.weight_in_kilos").value(1));

        List<Fruit> fruits = fruitRepository.findAll();

        assertEquals(1, fruits.size());

        Fruit savedFruit = fruits.getFirst();
        assertNotNull(savedFruit.getId());
        assertEquals("Strawberry", savedFruit.getName());
        assertEquals(1, savedFruit.getWeightInKilos());
    }

    @Test
    @DisplayName("PUT /fruits should modify given fruit in the database and return expected HTTP response")
    void updateFruit_shouldModifyFruitInDatabaseAndReturnExpectedResponse() throws Exception {

        Fruit initialFruit = fruitRepository.save(new Fruit("Apple", 2));
        Long id = initialFruit.getId();

        FruitRequestDto testUpdateRequest = new FruitRequestDto("Banana", 1);
        String jsonPayload = objectMapper.writeValueAsString(testUpdateRequest);

        mockMvc.perform(put("/fruits/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.weight_in_kilos").value(1));

        Optional<Fruit> allegedFruitFromH2 = fruitRepository.findById(id);

        if(allegedFruitFromH2.isEmpty()) {
            throw new Exception("Error");
        }

        Fruit fruitFromH2 = allegedFruitFromH2.get();

        assertEquals("Banana", fruitFromH2.getName());
        assertEquals(1, fruitFromH2.getWeightInKilos());
    }
}
