package market_nw.market_nw.test.google;

import lombok.RequiredArgsConstructor;

import market_nw.market_nw.repository.user.SocialLoginRepository;
import market_nw.market_nw.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static market_nw.market_nw.entity.user.SocialPlatformType.GOOGLE;

@Service
@RequiredArgsConstructor
public class GoogleService {

    private final SocialLoginRepository socialLoginRepository;
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




//
//resultentity란:<200,GoogleResponse(
//        access_token=ya29.a0AXooCgu2wkC090sK8EUgFn91YDoM6IyTZ8kPQjp6KMj8zDJQp0fJVpNoGd_Dt3FhfLAP4kC8EWggHnmi83_7BuuIP1nRqwI9zFbn-OkujAPRC0E6heOVS-cJ-wchjCgytEJr6jf6vVdU5AqDAW2AET8GWTtxJPi8MKL0aCgYKARoSARMSFQHGX2MizYe3SOHPE0csZ8t8TxjR6Q0171,
//        expires_in=3599,
//        refresh_token=1//0ecHM-02y4koxCgYIARAAGA4SNwF-L9IrnRc2PJs-5aT6bReXcmqaaiCu8lobKiVDYro0CsKSvumC9RuNOX0C3RURc2MCB5IpD1I,
//        scope=openid https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile,
//        token_type=Bearer, id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6IjJhZjkwZTg3YmUxNDBjMjAwMzg4OThhNmVmYTExMjgzZGFiNjAzMWQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI5MzE2MzM3MjI1OTYtZjhvdjFuOG4zbzEydTlta3QwaDUxZzdoZ2xmZ2RzZGkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5MzE2MzM3MjI1OTYtZjhvdjFuOG4zbzEydTlta3QwaDUxZzdoZ2xmZ2RzZGkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTI0MDEzMjY4MDUxOTAxMDIwMDIiLCJoZCI6ImN1LmFjLmtyIiwiZW1haWwiOiJ3aHdvMjE0MkBjdS5hYy5rciIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoidktKTmgwRnBlY2otMnhtUllzcVRjdyIsIm5hbWUiOiLsobDsnqzrprwiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jSUxMV3dHYjFpazJTMWctQlhpWm9HQXJMYVNSRHFpWlN1ODd2RHJWd1lfaG1YamFBPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IuyerOumvCIsImZhbWlseV9uYW1lIjoi7KGwIiwiaWF0IjoxNzE5ODgzMjcyLCJleHAiOjE3MTk4ODY4NzJ9.QqqBDtpLEreWocsUTla7UCAMejjc9Z8KehL9JLHKlf15n-IZZRMQM6EUJ2yYmjAasYQ4w3ewDmYBvwtEDVimZnH5MW2cWXSpHCvuaVSU2LFxDYfF0KKvtx0R-sByGF6OKNmtm9-9siPHe-J9VP70d3QkWjIJYyLTQ-2dqLUmfK3MVXVHzUHzx491lTC2NdDctNY5wSrO8ZIIXuoNtee1JnJ8quDQANzIf2qQb4HcrQ_6MNHl-YHdGqEVO95gdkmNII7m06YtKq8Xnxt7E7jfPVj1lEHuz_XkXeImdOBtjuq73fr2I6Lka5jEOvTFE-0hDNZtv353Y7pMer9WpCLKg),[Date:"Tue, 02 Jul 2024 01:21:12 GMT", Pragma:"no-cache", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Expires:"Mon, 01 Jan 1990 00:00:00 GMT", Content-Type:"application/json; charset=utf-8",
//        Vary:"X-Origin", "Referer", "Origin,Accept-Encoding", Server:"scaffolding on HTTPServer2",
//        X-XSS-Protection:"0", X-Frame-Options:"SAMEORIGIN", X-Content-Type-Options:"nosniff",
//        Alt-Svc:"h3=":443";
//        ma=2592000,h3-29=":443"; ma=2592000",
//        Accept-Ranges:"none", Transfer-Encoding:"chunked"]>
//
//        resultentity2란:<200,GoogleInfResponse(iss=https://accounts.google.com,
//        azp=931633722596-f8ov1n8n3o12u9mkt0h51g7hglfgdsdi.apps.googleusercontent.com,
//        aud=931633722596-f8ov1n8n3o12u9mkt0h51g7hglfgdsdi.apps.googleusercontent.com,
//        sub=112401326805190102002,
//        email=whwo2142@cu.ac.kr,
//        email_verified=true,
//        at_hash=vKJNh0Fpecj-2xmRYsqTcw,
//        name=조재림, picture=https://lh3.googleusercontent.com/a/ACg8ocILLWwGb1ik2S1g-BXiZoGArLaSRDqiZSu87vDrVwY_hmXjaA=s96-c,
//        given_name=재림, family_name=조, locale=null, iat=1719883272, exp=1719886872,
//        alg=RS256, kid=2af90e87be140c20038898a6efa11283dab6031d, typ=JWT),
//        [Pragma:"no-cache", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate",
//        Expires:"Mon, 01 Jan 1990 00:00:00 GMT", Date:"Tue, 02 Jul 2024 01:21:12 GMT",
//        Content-Type:"application/json; charset=UTF-8", Vary:"X-Origin", "Referer", "Origin,Accept-Encoding",
//        Server:"ESF", X-XSS-Protection:"0", X-Frame-Options:"SAMEORIGIN", X-Content-Type-Options:"nosniff",
//        Alt-Svc:"h3=":443"; ma=2592000,h3-29=":443"; ma=2592000", Accept-Ranges:"none", Transfer-Encoding:"chunked"]>
//
//
//
//
//        googleOAuthRequestParam이란 : GoogleRequest(
//        clientId=931633722596-f8ov1n8n3o12u9mkt0h51g7hglfgdsdi.apps.googleusercontent.com,
//        redirectUri=http://localhost:8080/api/v1/oauth2/google,
//        clientSecret=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,
//        responseType=null,
//        scope=null
//        , code=4/0ATx3LY5-MS49W3Dd8zqqY3eVNWyoEHllapNS0Bux93GsKBtUnBK-mD8XlYmscv1BB4CX0Q,
//        accessType=null,
//        grantType=authorization_code,
//        state=null,
//        includeGrantedScopes=null,
//        loginHint=null,
//        prompt=null)
