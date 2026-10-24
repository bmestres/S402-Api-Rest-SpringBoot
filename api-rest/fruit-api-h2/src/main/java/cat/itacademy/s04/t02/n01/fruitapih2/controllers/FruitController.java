package cat.itacademy.s04.t02.n01.fruitapih2.controllers;


import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapih2.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping
    public ResponseEntity<FruitResponseDto> createFruit(@Valid @RequestBody FruitRequestDto request) {
        FruitResponseDto createdFruit = fruitService.createFruit(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdFruit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitResponseDto> updateFruit(@PathVariable Long id, @Valid @RequestBody FruitRequestDto request) {
        FruitResponseDto updatedFruit = fruitService.updateFruit(id, request);

        return ResponseEntity.ok(updatedFruit);
    }


}
