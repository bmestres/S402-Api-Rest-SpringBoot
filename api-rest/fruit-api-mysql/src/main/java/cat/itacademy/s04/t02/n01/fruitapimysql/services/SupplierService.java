package cat.itacademy.s04.t02.n01.fruitapimysql.services;

import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierResponseDto;

import java.util.List;

public interface SupplierService {

    SupplierResponseDto registerSupplier(SupplierRequestDto supplierRequest);
    SupplierResponseDto updateSupplier(Long id, SupplierRequestDto supplierRequest);
    List<SupplierResponseDto> getAllSuppliers();
    SupplierRequestDto findSupplierById(Long id);
    void deleteSupplierById(Long id);

}
