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

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "country", nullable = false)
    private String country;

    public Supplier(String name, String country) {

        checkInputData(name, country);

        this.name = name;
        this.country = country;
    }

    private static void checkInputData(String name, String country) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be NULL");
        }
        if (name.trim().isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }

        if (country == null) {
            throw new IllegalArgumentException("Country must not be NULL");
        }
        if (country.trim().isBlank()) {
            throw new IllegalArgumentException("Country must not be empty");
        }
    }
}
