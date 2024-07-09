package market_nw.market_nw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    private String email; //email로찾는데 id보다 직관성이 있어보임.

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    private Boolean auto_flag; //자동로그인?
}
