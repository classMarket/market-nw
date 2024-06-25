package market_nw.market_nw.service;

import lombok.RequiredArgsConstructor;
import market_nw.market_nw.dto.LoginDto;
import market_nw.market_nw.entity.user.User;
import market_nw.market_nw.repository.UserRepository;
import market_nw.market_nw.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        System.out.println("test용 role" + user.getRole());
        if(user != null) {
            if(user.getPassword().equals(loginDto.getPassword())){
                return jwtTokenProvider.createToken(user.getEmailId(), user.getRole()); //로그인성공시 토큰반환
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
