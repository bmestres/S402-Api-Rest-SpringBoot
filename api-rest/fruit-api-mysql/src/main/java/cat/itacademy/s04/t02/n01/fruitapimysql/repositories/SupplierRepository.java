package cat.itacademy.s04.t02.n01.fruitapimysql.repositories;

import cat.itacademy.s04.t02.n01.fruitapimysql.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByName(String name);
}
