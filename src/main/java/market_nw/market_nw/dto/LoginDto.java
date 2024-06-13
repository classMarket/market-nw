package market_nw.market_nw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotEmpty
    private String email; //email로찾는데 id보다 직관성이 있어보임.
    @NotEmpty
    private String password;
    private Boolean auto_flag; //자동로그인?
}
