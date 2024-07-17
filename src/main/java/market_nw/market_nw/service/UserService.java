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

            if((passwordLogin.getPassword().equals(loginDto.getPassword()))){
                return jwtTokenProvider.createToken(users.getUserId(), users.getRole()); //로그인성공시 토큰반환
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> userOptional = usersRepository.findByUserId(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("유저찾기에 실패하였습니다. : " + email);
        }
        Users user = userOptional.get();
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserId())
                .password(user.getUserId())
                .roles(user.getRole())
                .build();
    }

    //소셜로그인용
    public String social_login(SocialPlatformType company, String email,String accessToken, String refreshToken) {
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
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .user(user)
                    .build();

            socialLoginRepository.save(socialLogin);
            System.out.println("신규유저입니다.");

        } else {//기존유저일시
            user = existingUser.get();
            Optional<SocialLogin> optionalSocialLogin = socialLoginRepository.findByEmail(email);

            if (optionalSocialLogin.isPresent()) {
                SocialLogin socialLogin = optionalSocialLogin.get();
                socialLogin.updateToken(accessToken, refreshToken); //토큰 업데이트
                socialLoginRepository.save(socialLogin);
            } else {
                System.out.println("기존 유저이지만 정보를 찾을 수 없습니다.");
            }

            System.out.println("기존유저입니다.");
        }
        return jwtTokenProvider.createToken(user.getUserId(), user.getRole()); //로그인성공시 토큰반환
    }

}
