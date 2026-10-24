package cat.itacademy.s04.t02.n01.fruitapih2.controllers;

import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapih2.services.FruitServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("POST /fruits request should return HTTP 201 Created with the fruit detail")
    void createFruit_shouldReturnExpectedHttpResponseWithDetails() throws Exception {

        FruitRequestDto testRequest = new FruitRequestDto("Banana", 5);
        FruitResponseDto expectedResponse = new FruitResponseDto(1L, testRequest.name(), testRequest.weightInKilos());

        String jsonPayload = objectMapper.writeValueAsString(testRequest);

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
}