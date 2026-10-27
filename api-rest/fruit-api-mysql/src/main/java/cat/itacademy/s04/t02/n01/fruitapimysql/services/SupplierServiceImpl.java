package cat.itacademy.s04.t02.n01.fruitapimysql.services;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierResponseDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.ExistingSupplierNameException;
import cat.itacademy.s04.t02.n01.fruitapimysql.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public SupplierResponseDto createSupplier(SupplierRequestDto supplierRequest) {
        if(supplierRequest == null) {
            throw new IllegalArgumentException("Request must not be NULL");
        }

        Supplier supplier = buildSupplierFromDto(supplierRequest);

        if(this.supplierRepository.existsByName(supplier)) {
            throw new ExistingSupplierNameException(String.format("Supplier %s already exists", supplier.getName()));
        }

        return buildDtoFromSupplier(this.supplierRepository.save(supplier));

    }

    @Override
    @Transactional
    public SupplierResponseDto updateSupplier(Long id, SupplierRequestDto supplierRequest) {
        return null;
    }

    @Override
    @Transactional
    public List<SupplierResponseDto> getAllSuppliers() {
        return List.of();
    }

    @Override
    @Transactional
    public SupplierRequestDto findSupplierById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void deleteSupplierById(Long id) {
    }

    Supplier buildSupplierFromDto(SupplierRequestDto request) {
        return new Supplier(
                request.name(),
                request.country());
    }

    SupplierResponseDto buildDtoFromSupplier(Supplier supplier) {
        return new SupplierResponseDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getCountry()
        );
    }


}
