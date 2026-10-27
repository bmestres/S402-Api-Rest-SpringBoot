package cat.itacademy.s04.t02.n01.fruitapimysql.services;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierResponseDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.SupplierNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapimysql.repositories.SupplierRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    @DisplayName("Register Supplier with non-existing ID should return expected DTO response")
    void registerSupplier_withNonExistingIdShouldReturnExpectedDTO() {

        SupplierRequestDto supplierRequest = new SupplierRequestDto("Name", "Country");

        Supplier savedSupplier = new Supplier(supplierRequest.name(), supplierRequest.country());
        savedSupplier.setId(1L);

        when(supplierRepository.save(any(Supplier.class))).thenReturn(savedSupplier);

        SupplierResponseDto responseSupplier = this.supplierService.registerSupplier(supplierRequest);

        assertNotNull(responseSupplier);
        assertEquals(1L, responseSupplier.id());
        assertEquals("Name", responseSupplier.name());
        assertEquals("Country", responseSupplier.country());

    }

    @Test
    @DisplayName("Update Supplier with existing ID should return expected DTO response")
    void updateSupplier_withExistingIdShouldReturnExpectedDTO() {

        Supplier existingSupplier = new Supplier("GreenSpace", "Italy");
        existingSupplier.setId(1L);

        SupplierRequestDto request = new SupplierRequestDto("Market Fruit", "Spain");

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(existingSupplier);

        SupplierResponseDto result = supplierService.updateSupplier(1L, request);

        assertEquals(1L, result.id());
        assertEquals("Market Fruit", result.name());
        assertEquals("Spain", result.country());
    }

    @Test
    @DisplayName("Update Supplier with non-existing ID should return SupplierNotFoundException")
    void updateSupplier_withNonExistingIdShouldThrowExpectedException() {

        Long nonExistingId = 29L;

        when(supplierRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class,
                ()-> supplierService.updateSupplier(nonExistingId, new SupplierRequestDto("Fields", "Netherlands")));

    }

    @Test
    void getAllSuppliers() {
    }

    @Test
    void findSupplierById() {
    }

    @Test
    void deleteSupplierById() {
    }
}