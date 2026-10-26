package cat.itacademy.s04.t02.n01.fruitapih2.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FruitTest {

    @Test
    void constructor_withValidParametersInitializesAttributes() {
        Fruit testFruit = new Fruit("Fruit", 2);

        assertEquals("Fruit", testFruit.getName());
        assertEquals(2, testFruit.weightInKilos);
    }

    @Test
    void constructor_withEmptyNameReturnsException() {
        assertThrows((IllegalArgumentException.class), () -> new Fruit("", 2));
    }

    @Test
    void constructor_withNegativeWeightValueReturnsException() {
        assertThrows((IllegalArgumentException.class), () -> new Fruit("Fruit", -2));
    }
}