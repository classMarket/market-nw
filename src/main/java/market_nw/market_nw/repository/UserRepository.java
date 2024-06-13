package market_nw.market_nw.repository;


import market_nw.market_nw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM market_user WHERE email_id = :id",nativeQuery = true)
    User findByEmail(@Param("id") String id);

    @Query(value = "SELECT m FROM User m WHERE m.emailId = :id")
    User findByEmail2(@Param("id") String id);
}




