package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponseDto;

import java.util.List;

public interface FruitService {
    FruitResponseDto createFruit(FruitRequestDto request);
    FruitResponseDto updateFruit(Long id, FruitRequestDto request);
    List<FruitResponseDto> getAllFruits();
    FruitResponseDto findById(Long id);
    void deleteFruit(Long id);
}
