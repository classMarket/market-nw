package market_nw.market_nw.repository.user;


import market_nw.market_nw.entity.user.UserProfile;
import market_nw.market_nw.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUser(Users user);
}
