package market_nw.market_nw.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "market_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq; // PK (Primary Key)

    @Column(name = "email_id",unique = true)
    private String emailId; // 이메일

    private String password; // 비밀번호

    @Column(name = "user_name")
    private String userName; // 이름

    private String provider; // 잘 모르겠네요

    @Column(name = "user_gender")
    private String userGender; // 성별

    @Column(name = "user_age")
    private String userAge; // 연령

    private String memo; // 메모?

    private BigDecimal point; // 포인트

    @Column(name = "phone_number")
    private String phoneNumber; // 전화번호

    private String img; // 유저 이미지?

    private String city; // 도시

    private String gu; // 구

    private String dong; // 동

    @Column(name = "sms_auth")
    private String smsAuth; // SMS 인증 여부

    @Column(name = "sms_auth_date")
    private LocalDateTime smsAuthDate; // SMS 인증 날짜

    @Column(name = "reg_date")
    private String regDate; // 등록 날짜

    @Column(name = "reg_id")
    private String regId; // 등록자 ID

    @Column(name = "mod_date")
    private String modDate; // 수정 날짜

    @Column(name = "mod_id")
    private String modId; // 수정자 ID

    private String role; // 역할 (권한) -> Admin,User

    @Column(name = "last_login")
    private LocalDateTime lastLogin; // 마지막 로그인 날짜

    @Column(name = "logout_date")
    private LocalDateTime logoutDate; // 로그아웃 날짜

    private String photo; // 사진

    @Column(name = "nick_name")
    private String nickName; // 닉네임

    @Column(name = "access_token")
    private String accessToken; // 액세스 토큰

    @Column(name = "profile_img")
    private String profileImg; // 프로필 이미지
}
