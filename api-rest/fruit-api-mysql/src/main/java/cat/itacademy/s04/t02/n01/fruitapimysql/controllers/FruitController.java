package cat.itacademy.s04.t02.n01.fruitapimysql.controllers;


import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.FruitResponseDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping
    public ResponseEntity<FruitResponseDto> createFruit(@Valid @RequestBody FruitRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fruitService.createFruit(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitResponseDto> updateFruit(@PathVariable Long id, @Valid @RequestBody FruitRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(fruitService.updateFruit(id, request));
    }

    @GetMapping
    public ResponseEntity<List<FruitResponseDto>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(fruitService.getAllFruits());
    }

   @GetMapping
    public ResponseEntity<List<FruitResponseDto>> listFruitsBySupplier(@RequestParam(name = "supplier_id") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(fruitService.getFruitsBySupplier(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitResponseDto> getFruitById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(fruitService.findFruitById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruitById(@PathVariable Long id) {
        fruitService.deleteFruitById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
