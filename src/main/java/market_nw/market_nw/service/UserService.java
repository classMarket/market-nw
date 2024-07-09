package market_nw.market_nw.service;

import lombok.RequiredArgsConstructor;
import market_nw.market_nw.dto.LoginDto;
import market_nw.market_nw.entity.user.PasswordLogin;
import market_nw.market_nw.entity.user.User_test;
import market_nw.market_nw.entity.user.Users;
import market_nw.market_nw.repository.user.PassWordLoginRepository;
import market_nw.market_nw.repository.user.UsersRepository;
import market_nw.market_nw.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PassWordLoginRepository passWordLoginRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String normal_login(LoginDto loginDto) {
        Optional<PasswordLogin> optionalPasswordLogin = passWordLoginRepository.findByEmail(loginDto.getEmail());
        if(optionalPasswordLogin.isPresent()) {
            PasswordLogin passwordLogin = optionalPasswordLogin.get();
            Users users = passwordLogin.getUsers();
            System.out.println("test용 role" + users.getRole());

            if((passwordLogin.getPassword().equals(loginDto.getPassword()))){
                return jwtTokenProvider.createToken(users.getUserId(), users.getRole()); //로그인성공시 토큰반환
            }
        }
        return null;
    }

    //기본적으로 소셜로그인시 이 서비스로 들어온다.
    public void social_login(String name, String email, String platform){


    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User_test user = usersRepository.findByEmail(username);
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
