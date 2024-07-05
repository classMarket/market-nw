package market_nw.market_nw.entity.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import market_nw.market_nw.entity.AuditingEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthentication extends AuditingEntity { //이메일 인증번호 db저장 -> 왜 필요한지 알아보기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String authenticationCode;


    @Builder
    public EmailAuthentication(String email, String authenticationCode) {
        this.email = email;
        this.authenticationCode = authenticationCode;
    }
}
