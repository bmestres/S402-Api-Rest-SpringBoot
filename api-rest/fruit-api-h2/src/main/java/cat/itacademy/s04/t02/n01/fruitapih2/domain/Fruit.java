package cat.itacademy.s04.t02.n01.fruitapih2.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Fruit {
    @Id
    @GeneratedValue
    Long id;

    String name;

    @JsonProperty("weight_in_kilos")
    int weightInKilos;
}
