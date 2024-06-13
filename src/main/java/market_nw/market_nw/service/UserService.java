package market_nw.market_nw.service;


import lombok.RequiredArgsConstructor;
import market_nw.market_nw.dto.LoginDto;
import market_nw.market_nw.entity.User;
import market_nw.market_nw.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if(user != null) {
            if(user.getPassword().equals(loginDto.getPassword())){
                return user;
            }
        }
        return null;
    }
}
