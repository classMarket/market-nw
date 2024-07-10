package market_nw.market_nw.entity.user;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import market_nw.market_nw.entity.AuditingEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends AuditingEntity {

    @Id
    private UUID verifiedId;

    @Column(unique = true)
    private String userId;

    @Enumerated(EnumType.STRING)
    private SignUpType signUpType;

    private String role; // 역할 (권한) -> Admin,User

    @Builder
    public Users (String userId, SignUpType signUpType,String role) {
        this.verifiedId = UUID.randomUUID();
        this.userId = userId;
        this.role = role;
        this.signUpType = signUpType;
    }
}
