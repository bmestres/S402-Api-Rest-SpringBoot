package cat.itacademy.s04.t02.n01.fruitapimysql.controllers;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapimysql.services.FruitServiceImpl;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        Supplier supplier = new Supplier("Name", "Country");
        supplier.setId(1L);
        Long supplierId = supplier.getId();

        FruitRequestDto testCreateRequest = new FruitRequestDto("Banana", 5, supplierId);
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

        Supplier supplier1 = new Supplier("Name1", "Country");
        Long supplierId1 = supplier1.getId();

        FruitRequestDto emptyNameRequest = new FruitRequestDto("", 5, supplierId1);

        String jsonPayload1 = objectMapper.writeValueAsString(emptyNameRequest);

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload1))
                .andExpect(status().isBadRequest()
        );

        Supplier supplier2 = new Supplier("Name2", "Country");
        Long supplierId2 = supplier2.getId();

        FruitRequestDto nonPositiveKilosRequest = new FruitRequestDto("Fruit", 0, supplierId2);
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

        Supplier supplier = new Supplier("Name", "Country");

        supplier.setId(1L);

        FruitRequestDto testUpdateRequest = new FruitRequestDto("Fruit", 2, 1L);
        FruitResponseDto expectedResponse = new FruitResponseDto(1L, testUpdateRequest.name(), testUpdateRequest.weightInKilos());

        when(fruitService
                .updateFruit(any(Long.class), any(FruitRequestDto.class)))
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

        Supplier supplier = new Supplier("Name", "Country");

        supplier.setId(1L);

        FruitRequestDto testUpdateRequest = new FruitRequestDto("Fruit", 4, 1L);
        String jsonPayload = objectMapper.writeValueAsString(testUpdateRequest);

        Long nonExistingId = 33L;

        when(fruitService.updateFruit(any(Long.class), any(FruitRequestDto.class)))
                .thenThrow(new FruitNotFoundException(String.format("Fruit with ID %d not found", nonExistingId)));

        mockMvc.perform(put("/fruits/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /fruits{id} with invalid data should return HTTP 400 Bad Request response")
    void updateFruit_withNonValidDataShouldReturnExpectedHttpResponse() throws Exception {

        Supplier supplier = new Supplier("Name", "Country");
        supplier.setId(1L);;


        FruitRequestDto testInvalidRequest = new FruitRequestDto("", 4, 1L);
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

    @Test
    @DisplayName("GET /fruits/{id} with existing ID should return expected HTTP 200 OK with details of the fruit")
    void getFruitById_withExistingIdShouldReturnExpectedStatusWithFruitDetails() throws Exception {

        FruitResponseDto expectedReturn = new FruitResponseDto(1L, "Prune", 2);

        when(fruitService.findFruitById(1L)).thenReturn(expectedReturn);

        mockMvc.perform(get("/fruits/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Prune"))
                .andExpect(jsonPath("$.weight_in_kilos").value(2));
    }

    @Test
    @DisplayName("GET /fruits/{id} with non-existing ID should return HTTP 404 NOT FOUND with explanation errorMessage")
    void getFruitById_withNonExistingIdShouldReturnExpectedStatusWithExplanation() throws Exception {

        Long nonExistingId = 38L;
        String message = String.format("Fruit with ID %d not found", nonExistingId);

        when(fruitService.findFruitById(nonExistingId))
                .thenThrow(new FruitNotFoundException(message));

        mockMvc.perform(get("/fruits/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(message));
    }

    @Test
    @DisplayName("DELETE /fruits/{id} with existing ID should return HTTP 204 NO CONTENT")
    void deleteFruitById_withExistingIdShouldReturnExpectedHttpResponse() throws Exception {

        Long existingId = 1L;

        FruitResponseDto expectedReturn = new FruitResponseDto(existingId, "Fruit", 1);

        when(fruitService.findFruitById(existingId)).thenReturn(expectedReturn);

        mockMvc.perform(delete("/fruits/{id}", existingId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /fruits/{id} with non-existing ID should return HTTP 404 NOT FOUND with Error Message")
    void deleteFruitById_withNonExistingIdShouldReturnExpectedHttpResponseWithMessage() throws Exception {

        Long nonExistingId = 44L;
        String message = String.format("Fruit with ID %d not found", nonExistingId);

        doThrow(new FruitNotFoundException(message))
                .when(fruitService)
                .deleteFruitById(nonExistingId);

        mockMvc.perform(delete("/fruits/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(message));
    }
}