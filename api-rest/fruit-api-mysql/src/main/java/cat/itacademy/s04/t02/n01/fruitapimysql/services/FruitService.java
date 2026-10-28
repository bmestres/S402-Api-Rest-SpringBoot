package cat.itacademy.s04.t02.n01.fruitapimysql.services;

import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitResponseDto;

import java.util.List;

public interface FruitService {
    FruitResponseDto createFruit(FruitRequestDto fruitRequest);
    FruitResponseDto updateFruit(Long id, FruitRequestDto fruitRequest);
    List<FruitResponseDto> getAllFruits();
    List<FruitResponseDto> getFruitsBySupplier(Long id);
    FruitResponseDto findFruitById(Long id);
    void deleteFruitById(Long id);
}
