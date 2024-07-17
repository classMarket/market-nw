package market_nw.market_nw.social.google.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleInfResponse {
    private String iss; // 발급자(issuer) - JWT를 발급한 주체
    private String azp; // 인증된 파티 - 구글 클라이언트 ID
    private String aud; // 수신자(audience) - 토큰의 대상 수신자
    private String sub; // 주체(subject) - 유저를 고유하게 식별하는 ID
    private String email; // 사용자의 이메일 주소
    private String email_verified; // 이메일 확인 여부 (true/false)
    private String at_hash; // 액세스 토큰의 해시 값
    private String name; // 사용자의 전체 이름
    private String picture; // 사용자의 프로필 사진 URL
    private String given_name; // 사용자의 이름
    private String family_name; // 사용자의 성
    private String locale; // 사용자의 로케일 (예: en, ko 등)
    private String iat; // JWT 발급 시간 (Unix 시간)
    private String exp; // JWT 만료 시간 (Unix 시간)
    private String alg; // 서명 알고리즘
    private String kid; // 키 식별자 - 서명 검증에 사용되는 키 식별자
    private String typ; // 토큰 타입 - JWT
}