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

    private String name; //이름

    private String city; //도시

    private String dong; //동

    private String gu; //구

    private LocalDate birth; //생년월일

    @Column(unique = true)
    private String phoneNumber; //휴대폰번호

    @Column(unique = true)
    private String nickname; //닉네임

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToOne
    @JoinColumn(name = "imgId")
    private Image image;

    @Builder
    public UserProfile(String name, String city, String dong, String gu, LocalDate birth, String phoneNumber, String nickname, Image image, Users users) {
        this.name = name;
        this.city = city;
        this.dong = dong;
        this.gu = gu;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.user = users;
        this.image = image;
    }
}
