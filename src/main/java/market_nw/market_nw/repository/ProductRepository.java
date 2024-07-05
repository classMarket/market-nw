package market_nw.market_nw.repository;


import market_nw.market_nw.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.seq = :seq")
    Optional<Product> findProductDetailsById(@Param("seq") Integer seq);
}