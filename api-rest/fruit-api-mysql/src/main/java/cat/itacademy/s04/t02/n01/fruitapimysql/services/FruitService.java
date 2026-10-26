package cat.itacademy.s04.t02.n01.fruitapimysql.services;

import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitResponseDto;

import java.util.List;

public interface FruitService {
    FruitResponseDto createFruit(FruitRequestDto request);
    FruitResponseDto updateFruit(Long id, FruitRequestDto request);
    List<FruitResponseDto> getAllFruits();
    FruitResponseDto findById(Long id);
    void deleteFruitById(Long id);
}
