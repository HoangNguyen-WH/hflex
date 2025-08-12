package wu.huang.hflex.config.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wu.huang.hflex.common.model.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse<?> handleException(Exception e) {
        return BaseResponse.builder()
                .commons(BaseResponse.commonsResponse.builder()
                        .code("")
                        .message(e.getMessage())
                        .type("")
                        .build())
                .paging(new BaseResponse.PagingResponse())
                .build();
    }
}
