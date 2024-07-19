package market_nw.market_nw.controller;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market_nw.market_nw.dto.LoginDto;
import market_nw.market_nw.security.JwtTokenProvider;
import market_nw.market_nw.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        String token = userService.normal_login(loginDto); //로그인 성공시 토큰으로 변환

        if (token == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "이메일 또는 비밀번호가 맞지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        // 로그인 성공시 토큰을 헤더에 추가하여 반환
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return ResponseEntity.ok()
                .headers(headers)
                .body("JWT token 로그인 성공");
    }

    @GetMapping("/test")
    public ResponseEntity<Claims> test(@RequestHeader("Authorization") String token) {
        Claims claims = jwtTokenProvider.getClaims(token);
        return ResponseEntity.ok().body(claims);
    }



}
