package cat.itacademy.s04.t02.n01.fruitapimysql.repositories;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RepositoryTest {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private FruitRepository fruitRepository;

    @Test
    @DisplayName("Save Supplier should properly save Supplier details")
    void saveSupplier_shouldSaveNameAndCountryToDatabase() {
        Supplier savedSupplier = supplierRepository.save(new Supplier("Piña Colada", "Brazil"));

        assertNotNull(savedSupplier.getId());
        assertTrue(this.supplierRepository.existsByName("Piña Colada"));
    }

    @Test
    @DisplayName("Save Supplier with repeated name in repository should throw Exception")
    void saveSupplier_shouldThrowExceptionWhenExistingName() {

        this.supplierRepository.save(new Supplier("Naranjito", "Spain"));

        assertThrows(Exception.class, ()-> supplierRepository.save(new Supplier("Naranjito", "Argentina")));
    }

}