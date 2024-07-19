package market_nw.market_nw.social.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market_nw.market_nw.service.UserService;
import market_nw.market_nw.social.kakao.dto.KakaoTokenResponseDto;
import market_nw.market_nw.social.kakao.dto.KakaoUserInfoResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static market_nw.market_nw.entity.user.SocialPlatformType.KAKAO;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final UserService userService;

    @Value("${kakao.client.id}")
    private String clientId;

    private String KAUTH_TOKEN_URL_HOST="https://kauth.kakao.com";
    private String KAUTH_USER_URL_HOST="https://kapi.kakao.com";


    public KakaoTokenResponseDto getAccessTokenFromKakao(String code, String tokenvalue) {
        System.out.println("이게 오류인가:" + clientId);
        System.out.println("code란:" + code);
        KakaoTokenResponseDto kakaoTokenResponseDto;
        try {
            kakaoTokenResponseDto = WebClient.builder()
                    .baseUrl(KAUTH_TOKEN_URL_HOST)
                    .build()
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/oauth/token")
                            .queryParam("grant_type", "authorization_code")
                            .queryParam("client_id", clientId)
                            .queryParam("code", code)
                            .build())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Kakao API 클라이언트 오류 발생")))
                    .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Kakao API 서버 오류 발생")))
                    .bodyToMono(KakaoTokenResponseDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Kakao API 요청 중 오류 발생: " + e.getRawStatusCode() + " " + e.getStatusText());
        }

        log.info("Access Token 이란 : ", kakaoTokenResponseDto.getAccessToken());
        log.info("Refresh Token 이란 : ", kakaoTokenResponseDto.getRefreshToken());
        log.info("idtoken 이란 : ", kakaoTokenResponseDto.getIdToken());
            return kakaoTokenResponseDto;


    }

    public String getJwtTokenFromKakao(String accessToken, String refreshToken) {
        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

//        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.getId());
//        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.getKakaoAccount().getProfile().getNickName());
//        log.info("[ Kakao Service ] ProfileImageUrl ---> {} ", userInfo.getKakaoAccount().getProfile().getProfileImageUrl());

        return userService.social_login(KAKAO,userInfo.kakaoAccount.getEmail(),accessToken,refreshToken); //jwt토큰 반환

    }
}
