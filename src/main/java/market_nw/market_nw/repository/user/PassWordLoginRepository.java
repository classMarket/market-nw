package market_nw.market_nw.repository.user;


import market_nw.market_nw.entity.user.EmailAuthentication;
import market_nw.market_nw.entity.user.PasswordLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassWordLoginRepository extends JpaRepository<PasswordLogin, Long> {

    Optional<PasswordLogin> findByEmail(String email);

}
