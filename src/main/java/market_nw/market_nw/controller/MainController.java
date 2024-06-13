package market_nw.market_nw.controller;


import lombok.RequiredArgsConstructor;
import market_nw.market_nw.dto.LoginDto;
import market_nw.market_nw.entity.User;
import market_nw.market_nw.repository.UserRepository;
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
    private final UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity test(){
        return ResponseEntity.ok(userRepository.findAll());
    }

//Binding Result 는 서버 사이드 렌더링 에서 쓰이기에 없앴습니다.
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto);

        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "이메일 또는 비밀번호가 맞지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 로그인 성공 시 유저 정보를 JSON 형식으로 반환
        return ResponseEntity.ok(user);
    }


}
