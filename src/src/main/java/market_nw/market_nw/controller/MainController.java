package market_nw.market_nw.controller;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import market_nw.market_nw.dto.LoginDto;
import market_nw.market_nw.security.JwtTokenProvider;
import market_nw.market_nw.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    
//Binding Result 는 서버 사이드 렌더링 에서 쓰이기에 없앴습니다.
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        String token = userService.login(loginDto); //로그인 성공시 토큰으로 변환

        if (token == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "이메일 또는 비밀번호가 맞지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        // 로그인 성공시 토큰 으로 반환
        return ResponseEntity.ok(token);
    }

    @GetMapping("/test")
    public Claims test(@RequestHeader("Authorization") String token) {
        return jwtTokenProvider.getClaims(token);
    }
}
