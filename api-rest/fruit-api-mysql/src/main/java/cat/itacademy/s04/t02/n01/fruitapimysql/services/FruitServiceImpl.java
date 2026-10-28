package cat.itacademy.s04.t02.n01.fruitapimysql.services;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.SupplierNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapimysql.repositories.FruitRepository;
import cat.itacademy.s04.t02.n01.fruitapimysql.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;
    private final SupplierRepository supplierRepository;

    public FruitServiceImpl(FruitRepository fruitRepository, SupplierRepository supplierRepository) {

        checkInputData(fruitRepository, supplierRepository);

        this.fruitRepository = fruitRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public FruitResponseDto createFruit(FruitRequestDto request) {
        if(request == null) {
            throw new IllegalArgumentException("Request must not be NULL");
        }

        if(!supplierRepository.existsById(request.supplierId())) {
            throw new SupplierNotFoundException(String.format("Supplier with ID %d not found", request.supplierId()));
        }

        Fruit newFruit = buildFruitFromDto(request);

        return buildDtoFromFruit(this.fruitRepository.save(newFruit));
    }

    @Override
    @Transactional
    public FruitResponseDto updateFruit(Long id, FruitRequestDto request) {
        if(request == null) {
            throw new IllegalArgumentException("Request must not be NULL");
        }

        Optional<Fruit> allegedFruit = this.fruitRepository.findById(id);

        if(allegedFruit.isEmpty()) {
            throw new FruitNotFoundException(String.format("Fruit with id %d not found", id));
        }

        Fruit existingFruit = allegedFruit.get();

        Supplier supplier = findSupplierById(request.supplierId());

        existingFruit.setName(request.name());
        existingFruit.setWeightInKilos(request.weightInKilos());
        existingFruit.setSupplier(supplier);

        return buildDtoFromFruit(this.fruitRepository.save(existingFruit));
    }

    @Override
    @Transactional
    public List<FruitResponseDto> getAllFruits() {

        List<Fruit> fruits = this.fruitRepository.findAll();

        return fruits.stream()
                        .map(fruit -> buildDtoFromFruit(fruit))
                        .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<FruitResponseDto> getFruitsBySupplier(Long id) {

        List<Fruit> fruitsBySupplier = this
                .fruitRepository
                .findAll()
                .stream()
                .filter(fruit -> id.equals(fruit.getSupplier().getId()))
                .toList();

        return fruitsBySupplier
                .stream()
                .map(fruit -> buildDtoFromFruit(fruit))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FruitResponseDto findFruitById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("ID must not be NULL");
        }

        Optional<Fruit> allegedFruit = this.fruitRepository.findById(id);

        if(allegedFruit.isEmpty()) {
            throw new FruitNotFoundException(String.format("Fruit with ID %d not found", id));
        }
        return buildDtoFromFruit(allegedFruit.get());
    }

    @Override
    @Transactional
    public void deleteFruitById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("ID must not be NULL");
        }
        Optional<Fruit> allegedFruit = this.fruitRepository.findById(id);

        if(allegedFruit.isEmpty()) {
            throw new FruitNotFoundException(String.format("Fruit with ID %d not found", id));
        }
        this.fruitRepository.deleteById(id);
    }

    private static void checkInputData(FruitRepository fruitRepository, SupplierRepository supplierRepository) {
        if(fruitRepository == null) {
            throw new IllegalArgumentException("Fruit Repository must not be NULL");
        }

        if(supplierRepository == null) {
            throw new IllegalArgumentException("Supplier Repository must not be NULL");
        }
    }

    private Supplier findSupplierById(Long supplierId) {
        Optional<Supplier> allegedSupplier = this.supplierRepository.findById(supplierId);

        if(allegedSupplier.isEmpty()) {
            throw new SupplierNotFoundException(String.format("Supplier with id %d not found", supplierId));
        }
        return allegedSupplier.get();
    }

    private FruitResponseDto buildDtoFromFruit(Fruit fruit) {
        return new FruitResponseDto(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos());
    }

   private Fruit buildFruitFromDto(FruitRequestDto request) {
        return new Fruit(
                request.name(),
                request.weightInKilos(),
                findSupplierById(request.supplierId())
        );
    }
}
