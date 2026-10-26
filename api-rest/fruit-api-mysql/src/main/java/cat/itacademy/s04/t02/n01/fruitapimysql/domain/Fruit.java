package cat.itacademy.s04.t02.n01.fruitapimysql.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fruits")
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight_in_kilos")
    private int weightInKilos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    public Fruit(String name, int weightInKilos, Supplier supplier) {

        checkInputData(name, weightInKilos);

        this.name = name;
        this.weightInKilos = weightInKilos;
        this.supplier = supplier;
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
