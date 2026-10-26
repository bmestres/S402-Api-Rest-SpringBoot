package cat.itacademy.s04.t02.n01.fruitapih2.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    int weightInKilos;

    public Fruit(String name, int weightInKilos) {

        checkInputData(name, weightInKilos);

        this.name = name;
        this.weightInKilos = weightInKilos;
    }

    private static void checkInputData(String name, int weightInKilos) {
        if(name == null) {
            throw new IllegalArgumentException("Name must not be NULL");
        }

        if(name.trim().isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }

        if(weightInKilos < 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
    }
}
