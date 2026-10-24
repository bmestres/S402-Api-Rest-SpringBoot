package cat.itacademy.s04.t02.n01.fruitapih2.repositories;

import cat.itacademy.s04.t02.n01.fruitapih2.domain.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
    boolean existsById(Long id);
}
