package cat.itacademy.s04.t02.n01.fruitapimysql.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Supplier(String name) {

        checkInputData(name);
        this.name = name;
    }

    private static void checkInputData(String name) {
        if(name == null) {
            throw new IllegalArgumentException("Name must not be NULL");
        }
        if(name.trim().isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
    }
}
