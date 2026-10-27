package cat.itacademy.s04.t02.n01.fruitapimysql.repositories;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import cat.itacademy.s04.t02.n01.fruitapimysql.exception.ExistingSupplierNameException;
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
    void saveSupplier_shouldSaveNameAndCountryToDatabase() {
        Supplier savedSupplier = supplierRepository.save(new Supplier("Piña Colada", "Brazil"));

        assertNotNull(savedSupplier.getId());
        assertTrue(this.supplierRepository.existsByName("Piña Colada"));
    }

    @Test
    void saveSupplier_shouldThrowExceptionWhenExistingName() {

        this.supplierRepository.save(new Supplier("Naranjito", "Spain"));

        assertThrows(Exception.class, ()-> supplierRepository.save(new Supplier("Naranjito", "Argentina")));
    }

}