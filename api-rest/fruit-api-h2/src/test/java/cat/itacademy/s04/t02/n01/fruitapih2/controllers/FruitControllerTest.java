package cat.itacademy.s04.t02.n01.fruitapih2.controllers;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapih2.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapih2.services.FruitServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Random;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FruitController.class)
class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FruitServiceImpl fruitService;

    @Test
    @DisplayName("POST /fruits request should return HTTP 201 Created and details of the fruit")
    void createFruit_shouldReturnExpectedHttpResponseWithDetails() throws Exception {

        FruitRequestDto testCreateRequest = new FruitRequestDto("Banana", 5);
        FruitResponseDto expectedResponse = new FruitResponseDto(1L, testCreateRequest.name(), testCreateRequest.weightInKilos());

        String jsonPayload = objectMapper.writeValueAsString(testCreateRequest);

        when(fruitService.createFruit(any(FruitRequestDto.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.weight_in_kilos").value(5));
    }

    @Test
    @DisplayName("POST/ fruits request should return HTTP 400 Bad request if the name is empty or the weight is not valid")
    void createFruit_shouldReturnExpectedHttpResponseWhenParametersAreNotValid() throws Exception {
        FruitRequestDto emptyNameRequest = new FruitRequestDto("", 5);

        String jsonPayload1 = objectMapper.writeValueAsString(emptyNameRequest);

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload1))
                .andExpect(status().isBadRequest()
        );

        FruitRequestDto nonPositiveKilosRequest = new FruitRequestDto("Fruit", 0);
        String jsonPayload2 = objectMapper.writeValueAsString(nonPositiveKilosRequest);

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload2))
                .andExpect(status().isBadRequest()
        );
    }

    @Test
    @DisplayName("PUT /fruits/{id} valid request should return HTTP 200 OK and details of the fruit")
    void updateFruit_shouldReturnExpectedHttpWithDetails() throws Exception {
        FruitRequestDto testUpdateRequest = new FruitRequestDto("Fruit", 2);
        FruitResponseDto expectedResponse = new FruitResponseDto(1L, testUpdateRequest.name(), testUpdateRequest.weightInKilos());

        when(fruitService.updateFruit(any(Long.class), any(FruitRequestDto.class)))
                .thenReturn(expectedResponse);

        String jsonPayload = objectMapper.writeValueAsString(testUpdateRequest);

        mockMvc.perform(put("/fruits/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fruit"))
                .andExpect(jsonPath("$.weight_in_kilos").value(2)
                );
    }

    @Test
    @DisplayName("PUT /fruits{id} with non-existing id should return HTTP 404 Not Found response")
    void updateFruit_withNonExistingIdShouldReturnExpectedHttpResponse() throws Exception {

        FruitRequestDto testUpdateRequest = new FruitRequestDto("Fruit", 4);
        String jsonPayload = objectMapper.writeValueAsString(testUpdateRequest);

        Long randomId = new Random().nextLong();

        when(fruitService.updateFruit(any(Long.class), any(FruitRequestDto.class)))
                .thenThrow(new FruitNotFoundException(String.format("Fruit with ID %d not found", randomId)));

        mockMvc.perform(put("/fruits/{id}", randomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /fruits{id} with invalid data should return HTTP 400 Bad Request response")
    void updateFruit_withNonValidDataShouldReturnExpectedHttpResponse() throws Exception {

        FruitRequestDto testInvalidRequest = new FruitRequestDto("", 4);
        String jsonPayload = objectMapper.writeValueAsString(testInvalidRequest);

        mockMvc.perform(put("/fruits/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /fruits with empty repository should return empty array with HTTP 200 OK status")
    void listAll_shouldReturnExpectedHttpStatusWithEmptyArrayIfNoElementsInRepository() throws Exception {

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("GET /fruits should return expected json array with HTTP OK status")
    void listAll_shouldReturnExpectedHttpStatusWithExpectedJsonArray() throws Exception {

        FruitResponseDto fruit1 = new FruitResponseDto(1L,"Tangerine", 2);
        FruitResponseDto fruit2 = new FruitResponseDto(2L,"Blueberry", 1);

        when(fruitService
                .getAllFruits())
                .thenReturn(List.of(
                fruit1,
                fruit2));

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(fruit1.name()))
                .andExpect(jsonPath("$[0].weight_in_kilos").value(fruit1.weightInKilos()))
                .andExpect(jsonPath("$[1].id").value(fruit2.id()))
                .andExpect(jsonPath("$[1].name").value(fruit2.name()))
                .andExpect(jsonPath("$[1].weight_in_kilos").value(fruit2.weightInKilos()));
    }
}