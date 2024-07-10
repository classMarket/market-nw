package market_nw.market_nw.repository.user;


import market_nw.market_nw.entity.user.PasswordLogin;
import market_nw.market_nw.entity.user.SocialLogin;
import market_nw.market_nw.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {
    Optional<PasswordLogin> findByEmail(String email);
}
