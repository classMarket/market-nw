package market_nw.market_nw.social.kakao.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market_nw.market_nw.social.kakao.dto.KakaoUserInfoResponseDto;
import market_nw.market_nw.social.kakao.service.KakaoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        System.out.println("여기까지는 오나:");
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        System.out.println("여기는?");

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        System.out.println("이게 유저 정보:" + userInfo.getKakaoAccount());

        return new ResponseEntity<>(HttpStatus.OK);//jwt토큰으로 반환하게하기
    }


}
