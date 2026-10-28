package cat.itacademy.s04.t02.n01.fruitapimysql.controllers;

import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierRequestDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.dto.SupplierResponseDto;
import cat.itacademy.s04.t02.n01.fruitapimysql.services.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDto> registerSupplier(@Valid @RequestBody SupplierRequestDto supplierRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.supplierService.registerSupplier(supplierRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierRequestDto supplierRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(this.supplierService.updateSupplier(id, supplierRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> deleteSupplier(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
