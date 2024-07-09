package market_nw.market_nw.entity.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import market_nw.market_nw.entity.AuditingEntity;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordLogin extends AuditingEntity {
    @Id
    private String email;

    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
