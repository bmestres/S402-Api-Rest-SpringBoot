package cat.itacademy.s04.t02.n01.fruitapimysql.repositories;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
}
