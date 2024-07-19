package market_nw.market_nw.social.google.service;

import lombok.RequiredArgsConstructor;

import market_nw.market_nw.repository.user.SocialLoginRepository;
import market_nw.market_nw.service.UserService;
import market_nw.market_nw.social.google.dto.GoogleInfResponse;
import market_nw.market_nw.social.google.dto.GoogleRequest;
import market_nw.market_nw.social.google.dto.GoogleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static market_nw.market_nw.entity.user.SocialPlatformType.GOOGLE;

@Service
@RequiredArgsConstructor
public class GoogleService {

    private final UserService userService;



    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.pw}")
    private String googleClientPw;


    public String processGoogleLogin(String authCode){
        RestTemplate restTemplate = new RestTemplate();
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientPw)
                .code(authCode)
                .redirectUri("http://localhost:8080/api/v1/oauth2/google")
                .grantType("authorization_code")
                .build();
        ResponseEntity<GoogleResponse> resultEntity = restTemplate.postForEntity
                ("https://oauth2.googleapis.com/token"
                        , googleOAuthRequestParam
                        ,GoogleResponse.class);

        String accessToken = resultEntity.getBody().getAccess_token();
        String idToken = resultEntity.getBody().getId_token();

        // Access Token을 사용하여 사용자 정보 요청
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<GoogleInfResponse> resultEntity2 = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                entity,
                GoogleInfResponse.class
        );

        String jwtToken=resultEntity.getBody().getId_token();
        Map<String, String> map=new HashMap<>();
        map.put("id_token",jwtToken);
//        ResponseEntity<GoogleInfResponse> resultEntity2 = restTemplate.postForEntity
//                ("https://oauth2.googleapis.com/tokeninfo", map, GoogleInfResponse.class);

        // 사용자 정보 출력
        System.out.println("사용자 이름: " + resultEntity2.getBody().getName());
        System.out.println("발급한 회사: " + resultEntity2.getBody().getIss());
        System.out.println("사용자 이메일: " + resultEntity2.getBody().getEmail());

        return userService.social_login(GOOGLE,resultEntity2.getBody().getEmail(),resultEntity.getBody().getAccess_token(),resultEntity.getBody().getRefresh_token()); //jwt토큰 반환하는걸로 설정해야함
    }
}
