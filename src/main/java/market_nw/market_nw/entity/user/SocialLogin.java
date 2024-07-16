package market_nw.market_nw.entity.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import market_nw.market_nw.entity.AuditingEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialLogin extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialLoginId;

    @Enumerated(EnumType.STRING)
    private SocialPlatformType platformType; // 회사

    private String email; // 식별하는 값
    private String accessToken; // 액세스 토큰
    private String refreshToken; // 리프레시 토큰

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Builder
    public SocialLogin(SocialPlatformType platformType, String email, Users user, String accessToken, String refreshToken) {
        this.platformType = platformType;
        this.email = email;
        this.user = user;
        this.accessToken= accessToken;
        this.refreshToken = refreshToken;
    }


    public void updateToken(String accessToken,String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }



}
