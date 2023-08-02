package com.swms.common.utils.exception;

import com.swms.common.utils.exception.code_enum.CommonErrorDescEnum;
import com.swms.common.utils.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @author sws
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpRequestMethodNotSupportedHandler(HttpServletResponse response) {
        ErrorResponse errorResponse = ErrorResponse.builder().message("Method Not Allow")
            .errorCode(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()))
            .build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> noHandlerFoundHandler(HttpServletResponse response) {
        ErrorResponse errorResponse = ErrorResponse.builder().message("Method Not Found")
            .errorCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(WmsException.class)
    public ResponseEntity<ErrorResponse> bizExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        WmsException wmsException = (WmsException) e;
        ErrorResponse errorResponse = ErrorResponse.builder().message("Business Error")
            .errorCode(wmsException.getCode())
            .description(wmsException.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

//    @ResponseBody
//    @ExceptionHandler(DuplicateKeyException.class)
//    public ResponseEntity<ErrorResponse> duplicateKeyExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
//        ErrorResponse errorResponse = ErrorResponse.builder().message("Database Error")
//            .errorCode(CommonErrorDescEnum.DATABASE_UNIQUE_ERROR.getCode())
//            .description(CommonErrorDescEnum.DATABASE_UNIQUE_ERROR.getDesc())
//            .build();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {

        ErrorResponse errorResponse = ErrorResponse.builder().message("System Error")
            .errorCode(CommonErrorDescEnum.SYSTEM_EXEC_ERROR.getCode())
            .description(CommonErrorDescEnum.SYSTEM_EXEC_ERROR.getDesc())
            .build();
        log.error("business catch exception error:", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalStateException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> httpRequestExceptionHandler(HttpMessageNotReadableException exception) {
        log.error("http request error: ", exception);
        ErrorResponse errorResponse = ErrorResponse.builder().message("Bad Request")
            .errorCode(CommonErrorDescEnum.HTTP_REQUEST_ERROR.getCode())
            .description(CommonErrorDescEnum.HTTP_REQUEST_ERROR.getDesc())
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 处理接口的数据检查异常
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request, HttpServletResponse response) {
        log.warn("interface parameter error: ", exception);
        // 包装一下校验结果
        List<Map<String, Object>> validResultMap = exception.getBindingResult().getFieldErrors().stream()
            .map(v -> {
                    Map<String, Object> fieldResultMap = new HashMap<>();
                    fieldResultMap.put("Field", v.getField());
                    fieldResultMap.put("rejectedValue", v.getRejectedValue());
                    fieldResultMap.put("defaultMessage", v.getDefaultMessage());
                    return fieldResultMap;
                }
            ).toList();
        ErrorResponse errorResponse = ErrorResponse.builder().message("Param Error")
            .errorCode(CommonErrorDescEnum.PARAMETER_ERROR.getCode())
            .description(JsonUtils.obj2String(validResultMap))
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
