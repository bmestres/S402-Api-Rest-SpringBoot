package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapih2.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapih2.repositories.FruitRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        Fruit newFruit = new Fruit(request.name(), request.weightInKilos());
        Fruit savedFruit = fruitRepository.save(newFruit);

        return buildDtoFromFruit(savedFruit);
    }

    @Override
    @Transactional
    public FruitResponseDto updateFruit(Long id, FruitRequestDto request) {
        if(request == null) {
            throw new IllegalArgumentException("Request must not be NULL");
        }
        if(id < 0) {
            throw new IllegalArgumentException("ID must not be NULL");
        }

        Optional<Fruit> foundFruit = fruitRepository.findById(id);

        if(foundFruit.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Fruit with id %d not found", id)
            );
        }

        Fruit fruit = foundFruit.get();

        fruit.setName(request.name());
        fruit.setWeightInKilos(request.weightInKilos());

        Fruit savedFruit = fruitRepository.save(fruit);

        return buildDtoFromFruit(savedFruit);
    }

    @Override
    public List<Fruit> getAllFruits() {
        return List.of();
    }

    @Override
    public Fruit findById(Long id) {
        return null;
    }

    @Override
    public void deleteFruit(Long id) {

    }

    private FruitResponseDto buildDtoFromFruit(Fruit fruit) {
        return new FruitResponseDto(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos());
    }
}
