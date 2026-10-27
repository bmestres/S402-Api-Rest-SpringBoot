package cat.itacademy.s04.t02.n01.fruitapimysql.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FruitTest {

    @Test
    @DisplayName("Constructor with valid parameters should initialize all fields")
    void constructor_withValidParametersInitializesAttributes() {

        Supplier supplier = new Supplier("Name", "Country");

        Fruit testFruit = new Fruit("Fruit", 2, supplier);

        assertEquals("Fruit", testFruit.getName());
        assertEquals(2, testFruit.getWeightInKilos());
        assertEquals(supplier, testFruit.getSupplier());
    }

    @Test
    @DisplayName("Constructor with empty fruit name should return exception")
    void constructor_withEmptyNameReturnsException() {

        Supplier supplier = new Supplier("Name", "Country");

        assertThrows((IllegalArgumentException.class), () -> new Fruit("", 2, supplier));
    }

    @Test
    @DisplayName("Constructor with negative weight value should return exception")
    void constructor_withNegativeWeightValueReturnsException() {

        Supplier supplier = new Supplier("Name", "Country");

        assertThrows((IllegalArgumentException.class), () -> new Fruit("Fruit", -2, supplier));
    }
}