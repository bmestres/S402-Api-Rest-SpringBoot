package cat.itacademy.s04.t02.n01.fruitapimysql.services;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierResponseDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.ExistingSupplierNameException;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.SupplierNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapimysql.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public SupplierResponseDto registerSupplier(SupplierRequestDto supplierRequest) {
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
        if(supplierRequest == null) {
            throw new IllegalArgumentException("Supplier request must not be NULL");
        }

        Optional<Supplier> allegedSupplier = this.supplierRepository.findById(id);

        if(allegedSupplier.isEmpty()) {
            throw new SupplierNotFoundException(String.format("Supplier with ID %d not found", id));
        }

        Supplier existingSupplier = allegedSupplier.get();

        existingSupplier.setName(supplierRequest.name());
        existingSupplier.setCountry(supplierRequest.country());

        return buildDtoFromSupplier(this.supplierRepository.save(existingSupplier));
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
