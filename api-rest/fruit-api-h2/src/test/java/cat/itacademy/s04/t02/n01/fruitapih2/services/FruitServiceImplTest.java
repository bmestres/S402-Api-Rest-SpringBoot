package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapih2.repositories.FruitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FruitServiceImplTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;


    @Test
    @DisplayName("createFruit should save fruit instance and return expected DTO response")
    void createFruit_shouldSaveInstanceAndReturnExpectedDto() {

        FruitRequestDto testCreateRequest = new FruitRequestDto("Orange", 3);
        Fruit savedFruit = new Fruit(testCreateRequest.name(), testCreateRequest.weightInKilos());
        savedFruit.setId(1L);

        when(fruitRepository.save(any(Fruit.class))).thenReturn(savedFruit);

        FruitResponseDto result = fruitService.createFruit(testCreateRequest);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Orange", result.name());
        assertEquals(3, result.weightInKilos());

    }

    @Test
    @DisplayName("Update fruit should modify existing food instance and return expected DTO response")
    void updateFruit_shouldModifyExistingInstanceAndReturnExpectedDto() {

        Fruit existingFruit = new Fruit("Mango", 1);
        Long id = new Random().nextLong();
        existingFruit.setId(id);

        FruitRequestDto testUpdateRequest = new FruitRequestDto("Banana", 2);

        when(fruitRepository
                .findById(id))
                .thenReturn(Optional.of(existingFruit));

        when(fruitRepository
                .save(any(Fruit.class)))
                .thenReturn(existingFruit);


        FruitResponseDto result = fruitService.updateFruit(id, testUpdateRequest);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("Banana", result.name());
        assertEquals(2, result.weightInKilos());
    }

    @Test
    void getAllFruits() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteFruit() {
    }
}