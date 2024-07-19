package market_nw.market_nw.social.kakao.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market_nw.market_nw.social.kakao.dto.KakaoTokenResponseDto;
import market_nw.market_nw.social.kakao.service.KakaoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @Value("${kakao.client.id}")
    private String client_id;

    @Value("${kakao.redirect.uri}")
    private String redirect_uri;

    @PostMapping("/api/v1/oauth2/kakao")
    public String loginPage() {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        return location;
    }

    @GetMapping("/api/v1/oauth2/kakao")
    public ResponseEntity<String> callback(@RequestParam("code") String code) {
        try {
            KakaoTokenResponseDto kakaoTokenResponseDto = kakaoService.getAccessTokenFromKakao(code, "AccessToken");
            String jwtToken = kakaoService.getJwtTokenFromKakao(kakaoTokenResponseDto.getAccessToken(), kakaoTokenResponseDto.getRefreshToken());//jwt토큰획득
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
            System.out.println("jwt토큰이란:" + jwtToken);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body("JWT token Kakao 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("JWT token Kakao 실패: " + e.getMessage());
        }
    }
}
