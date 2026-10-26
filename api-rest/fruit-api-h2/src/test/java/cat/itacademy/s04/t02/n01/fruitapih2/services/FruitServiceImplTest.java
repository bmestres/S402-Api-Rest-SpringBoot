package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapih2.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapih2.repositories.FruitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FruitServiceImplTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitServiceImpl fruitService;


    @Test
    @DisplayName("CreateFruit should call save Repository method and return expected DTO response")
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
    @DisplayName("Update fruit should call expected Repository methods and return expected DTO response")
    void updateFruit_shouldCallExpectedRepositoryMethodsAndReturnExpectedDto() {

        Fruit existingFruit = new Fruit("Mango", 1);
        Long id = 1L;
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
    @DisplayName("Get All Fruits in Fruit Service should return expected DTOs for each fruit in the repository")
    void getAllFruits_ShouldReturnExpectedDtoForEachFruitInRepository() {
        Fruit fruit1 = new Fruit("Peer", 2);
        Fruit fruit2 = new Fruit("Watermelon", 5);

        fruit1.setId(1L);
        fruit2.setId(2L);

        when(fruitRepository.findAll()).thenReturn(List.of(fruit1, fruit2));

        List<FruitResponseDto> result = fruitService.getAllFruits();

        assertEquals(1L, result.getFirst().id());
        assertEquals("Peer", result.getFirst().name());
        assertEquals(2, result.getFirst().weightInKilos());

        assertEquals(2L, result.get(1).id());
        assertEquals("Watermelon", result.get(1).name());
        assertEquals(5, result.get(1).weightInKilos());
    }

    @Test
    @DisplayName("Get Fruit By ID should return expected DTO if ID exists")
    void findById_shouldReturnExpectedDto() {

        Long existingId = 1L;
        Fruit existingFruit = new Fruit("Lemon", 3);
        existingFruit.setId(existingId);

        when(fruitRepository
                .findById(existingId))
                .thenReturn(Optional.of(existingFruit));

        FruitResponseDto responseFruit = fruitService.findById(existingId);

        assertNotNull(responseFruit);
        assertEquals(existingId, responseFruit.id());
        assertEquals("Lemon", responseFruit.name());
        assertEquals(3, responseFruit.weightInKilos());
    }

    @Test
    @DisplayName("Get Fruit by ID should return Fruit Not Found Exception if ID does not exist")
    void findById_shouldThrowExceptionIfIdDoesNotExist() {

        Long nonExistingId = 63L;

        when(fruitRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        FruitNotFoundException returnedException = assertThrows(FruitNotFoundException.class, () -> fruitService.findById(nonExistingId));
    }

    @Test
    @DisplayName("Delete fruit should call expected repository methods if ID exists")
    void deleteFruitById_shouldCallExpectedRepositoryMethodsIfIdExists() {

        Fruit existingFruit = new Fruit("Prune", 1);
        existingFruit.setId(1L);

        when(fruitRepository
                .findById(1L))
                .thenReturn(Optional.of(existingFruit));

        fruitService.deleteFruitById(1L);

        verify(fruitRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete fruit with non-existing ID should throw Fruit Not Found Exception")
    void deleteFruitById_withNonExistingIdShouldThrowExpectedException() {

        Long nonExistingId = 71L;

        when(fruitRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, ()-> fruitService.deleteFruitById(nonExistingId));
    }

}