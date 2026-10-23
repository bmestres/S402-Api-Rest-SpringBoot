package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequest;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponse;
import cat.itacademy.s04.t02.n01.fruitapih2.repositories.FruitRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public FruitResponse createFruit(FruitRequest request) {

        checkInputData(request);

        Fruit newFruit = new Fruit(request.name(), request.weightInKilos());
        Fruit savedFruit = fruitRepository.save(newFruit);

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

    private static void checkInputData(FruitRequest request) {
        if(request == null) {
            throw new IllegalArgumentException("Request must not be NULL");
        }
        if(request.name() == null) {
            throw new IllegalArgumentException("Name must not be NULL");
        }
        if(request.name().trim().isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
    }

    private FruitResponse buildDtoFromFruit(Fruit fruit) {
        return new FruitResponse(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos());
    }
}
