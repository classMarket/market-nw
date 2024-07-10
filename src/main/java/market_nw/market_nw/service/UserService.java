package market_nw.market_nw.service;

import lombok.RequiredArgsConstructor;
import market_nw.market_nw.dto.LoginDto;
import market_nw.market_nw.entity.user.*;
import market_nw.market_nw.repository.user.PassWordLoginRepository;
import market_nw.market_nw.repository.user.SocialLoginRepository;
import market_nw.market_nw.repository.user.UsersRepository;
import market_nw.market_nw.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static market_nw.market_nw.entity.user.SignUpType.SOCIAL;
import static market_nw.market_nw.entity.user.SocialPlatformType.GOOGLE;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PassWordLoginRepository passWordLoginRepository;
    private final SocialLoginRepository socialLoginRepository;
    private final UsersRepository usersRepository;
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    //소셜로그인용
    public String social_login(SocialPlatformType company, String email) {
        Optional<Users> existingUser = usersRepository.findByUserId(email);
        Users user;
        if (existingUser.isEmpty()) { //신규유저일시
            user = Users.builder()
                    .userId(email)
                    .signUpType(SOCIAL)
                    .role("USER")
                    .build();
            user = usersRepository.save(user);
            SocialLogin socialLogin = SocialLogin.builder()
                    .platformType(company)
                    .email(email)
                    .user(user)
                    .build();
            socialLoginRepository.save(socialLogin);
            System.out.println("신규유저입니다.");

        } else {//기존유저일시
            user = existingUser.get();
            System.out.println("기존유저입니다.");
        }
        return jwtTokenProvider.createToken(user.getUserId(), user.getRole()); //로그인성공시 토큰반환
    }



//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<PasswordLogin> optionalPasswordLogin = passWordLoginRepository.findByEmail(email);
//        Users users = usersRepository.findById(optionalPasswordLogin.get().getUsers().getVerifiedId());
//        User_test user = usersRepository.findByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUserName())
//                .password(user.getPassword())
//                .roles(user.getRole())
//                .build();
//    }
}
