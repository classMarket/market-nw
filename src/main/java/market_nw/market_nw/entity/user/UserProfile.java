package market_nw.market_nw.entity.user;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import market_nw.market_nw.entity.AuditingEntity;

import javax.persistence.*;
import java.time.LocalDate;
import market_nw.market_nw.entity.image.Image;




@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userProfileId;

    private String name;

    private String city;

    private String dong;

    private String gu;

    private LocalDate birth;

    private boolean sms_agreement;//sms 수신여부

    private boolean email_agreement; //email 수신여부

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String nickname;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToOne
    @JoinColumn(name = "imgId")
    private Image image;

    @Builder
    public UserProfile(String name, String city, String dong, String gu, LocalDate birth, String phoneNumber, String nickname, Image image, Users users, Boolean email_agreement, Boolean sms_agreement) {
        this.name = name;
        this.city = city;
        this.dong = dong;
        this.gu = gu;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.email_agreement = email_agreement;
        this.sms_agreement = sms_agreement;
        this.user = users;
        this.image = image;
    }
}
