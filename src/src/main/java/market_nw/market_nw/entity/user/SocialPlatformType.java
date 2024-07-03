package market_nw.market_nw.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum SocialPlatformType {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    final private String platformName;

    static public SocialPlatformType of(String platformName) {
        return Arrays.stream(values())
                     .filter(val -> Objects.equals(platformName, val.platformName))
                     .findFirst()
                     .orElseThrow();
    }
}

