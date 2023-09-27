package com.swms.exception.handler;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.CommonErrorDescEnum;
import com.swms.common.utils.language.core.LanguageContext;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.international.sdk.client.I18nApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

import java.text.MessageFormat;
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

    @Autowired
    private I18nApi i18nApi;

    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<SwmsErrorResponse> httpRequestMethodNotSupportedHandler(HttpServletResponse response) {
        SwmsErrorResponse errorResponse = SwmsErrorResponse.builder().message("Method Not Allow")
            .errorCode(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()))
            .description(i18nApi.getEntryValue(HttpStatus.METHOD_NOT_ALLOWED.name(), LanguageContext.getLanguage()))
            .build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<SwmsErrorResponse> noHandlerFoundHandler(HttpServletResponse response) {
        SwmsErrorResponse errorResponse = SwmsErrorResponse.builder().message("Method Not Found")
            .errorCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
            .description(i18nApi.getEntryValue(HttpStatus.NOT_FOUND.name(), LanguageContext.getLanguage()))
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(WmsException.class)
    public ResponseEntity<SwmsErrorResponse> bizExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        WmsException wmsException = (WmsException) e;
        String description = MessageFormat.format(i18nApi.getEntryValue(wmsException.getCode(),
            LanguageContext.getLanguage()), wmsException.getArgs());
        SwmsErrorResponse errorResponse = SwmsErrorResponse.builder().message("Business Error")
            .errorCode(wmsException.getCode())
            .description(description)
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<SwmsErrorResponse> duplicateKeyExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        SwmsErrorResponse errorResponse = SwmsErrorResponse.builder().message("Database Error")
            .errorCode(CommonErrorDescEnum.DATABASE_UNIQUE_ERROR.getCode())
            .description(i18nApi.getEntryValue(CommonErrorDescEnum.DATABASE_UNIQUE_ERROR.getDesc(), LanguageContext.getLanguage()))
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SwmsErrorResponse> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {

        SwmsErrorResponse errorResponse = SwmsErrorResponse.builder().message("System Error")
            .errorCode(CommonErrorDescEnum.SYSTEM_EXEC_ERROR.getCode())
            .description(i18nApi.getEntryValue(CommonErrorDescEnum.SYSTEM_EXEC_ERROR.getDesc(), LanguageContext.getLanguage()))
            .build();
        log.error("business catch exception error:", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalStateException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<SwmsErrorResponse> httpRequestExceptionHandler(HttpMessageNotReadableException exception) {
        log.error("http request error: ", exception);
        SwmsErrorResponse errorResponse = SwmsErrorResponse.builder().message("Bad Request")
            .errorCode(CommonErrorDescEnum.HTTP_REQUEST_ERROR.getCode())
            .description(i18nApi.getEntryValue(CommonErrorDescEnum.HTTP_REQUEST_ERROR.getDesc(), LanguageContext.getLanguage()))
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 处理接口的数据检查异常
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<SwmsErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request, HttpServletResponse response) {
        log.warn("interface parameter error: ", exception);
        List<Map<String, Object>> validResultMap = exception.getBindingResult().getFieldErrors().stream()
            .map(v -> {
                    Map<String, Object> fieldResultMap = new HashMap<>();
                    fieldResultMap.put("Field", v.getField());
                    fieldResultMap.put("rejectedValue", v.getRejectedValue());
                    fieldResultMap.put("defaultMessage", v.getDefaultMessage());
                    return fieldResultMap;
                }
            ).toList();
        SwmsErrorResponse errorResponse = SwmsErrorResponse.builder().message("Param Error")
            .errorCode(CommonErrorDescEnum.PARAMETER_ERROR.getCode())
            .description(JsonUtils.obj2String(validResultMap))
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
