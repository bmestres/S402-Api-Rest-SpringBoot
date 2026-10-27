package cat.itacademy.s04.t02.n01.fruitapimysql.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FruitTest {

    @Test
    void constructor_withValidParametersInitializesAttributes() {

        Supplier supplier = new Supplier("Name");

        Fruit testFruit = new Fruit("Fruit", 2, supplier);

        assertEquals("Fruit", testFruit.getName());
        assertEquals(2, testFruit.getWeightInKilos());
    }

    @Test
    void constructor_withEmptyNameReturnsException() {

        Supplier supplier = new Supplier("Name");

        assertThrows((IllegalArgumentException.class), () -> new Fruit("", 2, supplier));
    }

    @Test
    void constructor_withNegativeWeightValueReturnsException() {

        Supplier supplier = new Supplier("Name");

        assertThrows((IllegalArgumentException.class), () -> new Fruit("Fruit", -2, supplier));
    }
}