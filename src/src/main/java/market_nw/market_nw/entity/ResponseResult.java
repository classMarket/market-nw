package market_nw.market_nw.entity;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseResult<T> {
    private final String rs_code;
    private final T response;
    private final String errorMsg;
    private final HttpStatus status;

    public ResponseResult(String responseCode, T response, String errorMsg, HttpStatus status) {
        this.rs_code = responseCode;
        this.response = response;
        this.errorMsg = errorMsg;
        this.status = status;
    }

    public static <T> ResponseResult<T> of(String code, HttpStatus status, String message, T data) {
        return new ResponseResult<>(code, data, message, status);
    }

    public static <T> ResponseResult<T> success(String responseCode, T response) {
        return new ResponseResult<>(responseCode, response, null, HttpStatus.OK);
    }
}
