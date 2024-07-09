package market_nw.market_nw.test;

import lombok.RequiredArgsConstructor;
import market_nw.market_nw.test.google.GoogleInfResponse;
import market_nw.market_nw.test.google.GoogleRequest;
import market_nw.market_nw.test.google.GoogleResponse;
import market_nw.market_nw.test.google.GoogleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginController {

    private final GoogleService googleService;
    private final UserRepository usersRepository;

    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.pw}")
    private String googleClientPw;

    @PostMapping("/api/v1/oauth2/google")
    public String loginUrlGoogle(){
//        요청시 로그인 가능한 주소가 날라가게 됩니다.
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=http://localhost:8080/api/v1/oauth2/google&response_type=code&scope=email%20profile%20openid&access_type=offline";
        return reqUrl;
    }

    @GetMapping("/api/v1/oauth2/google")
    public String loginGoogle(@RequestParam(value = "code") String authCode){

        googleService.test(authCode); //여기서 jwt 토큰 생성해서 반환해줄것

        return "hello"; //jwt 토큰만 반환해줄것.
    }
}

