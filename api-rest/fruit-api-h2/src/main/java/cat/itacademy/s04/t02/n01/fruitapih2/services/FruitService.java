package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequest;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponse;

import java.util.List;

public interface FruitService {
    FruitResponse createFruit(FruitRequest request);
    List<Fruit> getAllFruits();
    Fruit findById(Long id);
    void deleteFruit(Long id);
}
