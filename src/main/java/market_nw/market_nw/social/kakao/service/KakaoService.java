package market_nw.market_nw.social.kakao.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    @Value("${kakao.client.id}")
    private String clientId;

    private String KAUTH_TOKEN_URL_HOST="https://kauth.kakao.com";
    private String KAUTH_USER_URL_HOST="https://kapi.kakao.com";

//    public KakaoService(@Value("${kakao.client_id}") String clientId) {
//        this.clientId = clientId;
//        KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
//        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
//    }

    public String getAccessTokenFromKakao(String code) {
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
            // WebClientResponseException은 Kakao API의 4xx 또는 5xx 오류를 포함하는 예외입니다.
            throw new RuntimeException("Kakao API 요청 중 오류 발생: " + e.getRawStatusCode() + " " + e.getStatusText());
        }

        log.info(" [Kakao Service] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
        log.info(" [Kakao Service] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());
        log.info(" [Kakao Service] Id Token ------> {}", kakaoTokenResponseDto.getIdToken());

        return kakaoTokenResponseDto.getAccessToken();
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
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

        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.getId());
        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.getKakaoAccount().getProfile().getNickName());
        log.info("[ Kakao Service ] ProfileImageUrl ---> {} ", userInfo.getKakaoAccount().getProfile().getProfileImageUrl());

        return userInfo;
    }
}
