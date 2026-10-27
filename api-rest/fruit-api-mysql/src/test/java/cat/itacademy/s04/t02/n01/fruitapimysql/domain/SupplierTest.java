package cat.itacademy.s04.t02.n01.fruitapimysql.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {

    @Test
    @DisplayName("Constructor with valid parameters should initialize fields")
    void constructor_withValidParametersShouldInitializeFields() {

        Supplier testSupplier = new Supplier("Correct name");
        assertEquals("Correct name", testSupplier.getName());
    }

    @Test
    @DisplayName("Constructor with empty name should return ")






}