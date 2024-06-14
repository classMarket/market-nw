package market_nw.market_nw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market_nw.market_nw.entity.User;
import market_nw.market_nw.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitUserData {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) { // 데이터가 없을 경우에만 초기화
            insertUser("test@test.com", "password", "홍길동", "local", "male", "30", "memo", BigDecimal.ZERO, "01012345678", "", "서울", "종로구", "청진동", "Y", LocalDateTime.now(), "2024-06-24", "admin", null, "2024-06-24", LocalDateTime.now(), LocalDateTime.now(), null, "test", "accessToken", "profileImg","User");
            insertUser("user2@test.com", "password", "김철수", "local", "male", "25", "memo", BigDecimal.ZERO, "01023456789", "", "서울", "강남구", "역삼동", "Y", LocalDateTime.now(), "2024-06-24", "admin", null, "2024-06-24", LocalDateTime.now(), LocalDateTime.now(), null, "test", "accessToken", "profileImg","User");

            System.out.println("유저 데이터 추가 완료");
        } else {
            System.out.println("유저 데이터가 이미 존재함.");
        }
    }

    private void insertUser(String emailId, String password, String userName, String provider, String userGender,
                            String userAge, String memo, BigDecimal point, String phoneNumber, String img, String city,
                            String gu, String dong, String smsAuth, LocalDateTime smsAuthDate, String regId,
                            String regDate, String modId, String modDate, LocalDateTime lastLogin,
                            LocalDateTime logoutDate, String photo, String nickName, String accessToken, String profileImg,String Roles) {
        User user = new User();
        user.setEmailId(emailId);
        user.setPassword(password);
        user.setUserName(userName);
        user.setProvider(provider);
        user.setUserGender(userGender);
        user.setUserAge(userAge);
        user.setMemo(memo);
        user.setPoint(point);
        user.setPhoneNumber(phoneNumber);
        user.setImg(img);
        user.setCity(city);
        user.setGu(gu);
        user.setDong(dong);
        user.setSmsAuth(smsAuth);
        user.setSmsAuthDate(smsAuthDate);
        user.setRegId(regId);
        user.setRegDate(regDate);
        user.setModId(modId);
        user.setModDate(String.valueOf(modDate));
        user.setLastLogin(lastLogin);
        user.setLogoutDate(logoutDate);
        user.setPhoto(photo);
        user.setNickName(nickName);
        user.setAccessToken(accessToken);
        user.setProfileImg(profileImg);
        user.setRole(Roles);

        userRepository.save(user);
    }
}
