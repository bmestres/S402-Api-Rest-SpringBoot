package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapih2.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapih2.repositories.FruitRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        if(fruitRepository == null) {
            throw new IllegalArgumentException("Repository must not be NULL");
        }
        this.fruitRepository = fruitRepository;
    }

    @Override
    @Transactional
    public FruitResponseDto createFruit(FruitRequestDto request) {
        if(request == null) {
            throw new IllegalArgumentException("Request must not be NULL");
        }

        Fruit newFruit = buildFruitFromDto(request);

        return buildDtoFromFruit(fruitRepository.save(newFruit));
    }

    @Override
    @Transactional
    public FruitResponseDto updateFruit(Long id, FruitRequestDto request) {
        if(request == null) {
            throw new IllegalArgumentException("Request must not be NULL");
        }

        Optional<Fruit> allegedFruit = fruitRepository.findById(id);

        if(allegedFruit.isEmpty()) {
            throw new FruitNotFoundException(String.format("Fruit with id %d not found", id));
        }

        Fruit existingFruit = allegedFruit.get();

        existingFruit.setName(request.name());
        existingFruit.setWeightInKilos(request.weightInKilos());

        return buildDtoFromFruit(fruitRepository.save(existingFruit));
    }

    @Override
    public List<FruitResponseDto> getAllFruits() {

        List<Fruit> fruits = fruitRepository.findAll();

        return fruits.stream()
                        .map(fruit -> buildDtoFromFruit(fruit))
                        .collect(Collectors.toList());
    }

    @Override
    public Fruit findById(Long id) {
        return null;
    }

    @Override
    public void deleteFruit(Long id) {

    }

    private static FruitResponseDto buildDtoFromFruit(Fruit fruit) {
        return new FruitResponseDto(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos());
    }

    private static Fruit buildFruitFromDto(FruitRequestDto request) {
        return new Fruit(
                request.name(),
                request.weightInKilos()
        );
    }
}
