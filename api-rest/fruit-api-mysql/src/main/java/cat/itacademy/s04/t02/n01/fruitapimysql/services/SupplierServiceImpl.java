package cat.itacademy.s04.t02.n01.fruitapimysql.services;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierResponseDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.RepeatedSupplierNameException;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.SupplierNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.SupplierWithFruitsAssociatedException;
import cat.itacademy.s04.t02.n01.fruitapimysql.repositories.FruitRepository;
import cat.itacademy.s04.t02.n01.fruitapimysql.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    SupplierRepository supplierRepository;
    FruitRepository fruitRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository, FruitRepository fruitRepository) {

        checkDependencies(supplierRepository, fruitRepository);

        this.supplierRepository = supplierRepository;
        this.fruitRepository = fruitRepository;
    }

    @Override
    @Transactional
    public SupplierResponseDto registerSupplier(SupplierRequestDto supplierRequest) {
        if(supplierRequest == null) {
            throw new IllegalArgumentException("Request must not be NULL");
        }


        Supplier supplier = buildSupplierFromDto(supplierRequest);

        if(this.supplierRepository.existsByName(supplier.getName())) {
            throw new RepeatedSupplierNameException(String.format("Supplier %s already exists", supplier.getName()));
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

        if(!this.supplierRepository.existsById(id)) {
            throw new SupplierNotFoundException(String.format("Supplier with ID %d not found", id));
        }

        if(this.fruitRepository.existsBySupplierId(id)) {
            throw new SupplierWithFruitsAssociatedException("Supplier with fruits associated");
        }

        this.supplierRepository.deleteById(id);
    }

    private static void checkDependencies(SupplierRepository supplierRepository, FruitRepository fruitRepository) {
        if(supplierRepository == null) {
            throw new IllegalArgumentException("Supplier Repository must not be NULL");
        }

        if(fruitRepository == null) {
            throw new IllegalArgumentException("Fruit repository must not be NULL");
        }
    }

    private static Supplier buildSupplierFromDto(SupplierRequestDto request) {
        return new Supplier(
                request.name(),
                request.country());
    }

    private static SupplierResponseDto buildDtoFromSupplier(Supplier supplier) {
        return new SupplierResponseDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getCountry()
        );
    }
}
