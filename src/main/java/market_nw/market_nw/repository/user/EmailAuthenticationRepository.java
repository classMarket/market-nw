package market_nw.market_nw.repository.user;


import market_nw.market_nw.entity.user.EmailAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAuthenticationRepository extends JpaRepository<EmailAuthentication, Long> {}
