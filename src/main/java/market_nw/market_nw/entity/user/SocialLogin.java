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

    @Enumerated(EnumType.STRING) //Original:열거형,String:문자열
    private SocialPlatformType platformType; //플랫폼명

    private String accessToken;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Builder
    public SocialLogin(String platformName, String accessToken, Users user) {
        this.platformType = SocialPlatformType.of(platformName);
        this.accessToken = accessToken;
        this.user = user;
    }
}
