package cat.itacademy.s04.t02.n01.fruitapimysql.controllers;


import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierResponseDto;

import cat.itacademy.s04.t02.n01.fruitapimysql.exception.SupplierNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapimysql.services.SupplierServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import static org.mockito.ArgumentMatchers.any;


import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SupplierServiceImpl supplierService;

    @Test
    @DisplayName("POST /suppliers valid request should return HTTP 201 Created and details of the supplier")
    void registerSupplier_shouldReturnExpectedHttpResponseWithSupplierDetails() throws Exception {

        SupplierRequestDto testRegisterRequest = new SupplierRequestDto("Name", "Country");
        SupplierResponseDto expectedResponse = new SupplierResponseDto(1L, testRegisterRequest.name(), testRegisterRequest.country());

        String jsonPayload = objectMapper.writeValueAsString(testRegisterRequest);

        when(supplierService.registerSupplier(any(SupplierRequestDto.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/suppliers").contentType(MediaType.APPLICATION_JSON).content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Name"))
                .andExpect(jsonPath("$.country").value("Country"));
    }

    @Test
    @DisplayName("PUT /suppliers valid request should return HTTP 200 OK and details of the supplier")
    void updateSupplier_validRequestShouldReturnExpectedHttpWithDetails() throws Exception {

        SupplierRequestDto testUpdateRequest = new SupplierRequestDto("Name", "France");
        SupplierResponseDto expectedResponse = new SupplierResponseDto(1L, testUpdateRequest.name(), testUpdateRequest.country());

        when(supplierService
                .updateSupplier(any(Long.class), any(SupplierRequestDto.class)))
                .thenReturn(expectedResponse);

        String jsonPayload = objectMapper.writeValueAsString(testUpdateRequest);

        mockMvc.perform(put("/suppliers/{id}", 1L).contentType(MediaType.APPLICATION_JSON).content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value( 1L))
                .andExpect(jsonPath("$.name").value("Name"))
                .andExpect(jsonPath("$.country").value("France"));
    }

    @Test
    @DisplayName("PUT /suppliers request with non-existing ID should return HTTP 404 NOT FOUND response")
    void updateSupplier_withNonExistingIdShouldReturnException() throws Exception {

        SupplierRequestDto testUpdateRequest = new SupplierRequestDto("Name", "Country");
        String jsonPayload = objectMapper.writeValueAsString(testUpdateRequest);

        Long nonExistingId = 82L;

        when(supplierService.updateSupplier(any(Long.class), any(SupplierRequestDto.class)))
                .thenThrow(new SupplierNotFoundException(String.format("Supplier with ID %d not found", nonExistingId)));

        mockMvc.perform(put("/suppliers/{id}", nonExistingId).contentType(MediaType.APPLICATION_JSON).content(jsonPayload))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /suppliers/{id} request with existing ID and no fruits associated should return HTTP 204 NO CONTENT")
    void deleteSupplierById_withExistingSupplierAndNoFruitsAssociatedShouldReturnExpectedHttpResponse() throws Exception {

        Long existingId = 1L;

        doNothing().when(supplierService).deleteSupplierById(existingId);

        mockMvc.perform(delete("/suppliers/{id}", existingId))
                .andExpect(status().isNoContent());
    }
}