package cat.itacademy.s04.t02.n01.fruitapimysql.repositories;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
    boolean existsBySupplierId(Long id);
    List<Fruit> findBySupplierId(Long supplierId);
}
