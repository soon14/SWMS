package com.swms.common.exception;

import com.swms.common.exception.code_enum.CommonErrorDescEnum;
import com.swms.common.http.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.lang.reflect.InvocationTargetException;

/**
 * 全局异常处理
 *
 * @author niekang
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String httpRequestMethodNotSupportedHandler(HttpServletResponse response) {
        return "method not allow";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundHandler(HttpServletResponse response) {
        response.setStatus(200);
        return "not found";
    }

    @ResponseBody
    @ExceptionHandler(WmsException.class)
    public Response<Object> bizExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        WmsException wmsException = (WmsException) e;
        String code = wmsException.getCode();
        return Response.builder().code(code).msg(wmsException.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public Object duplicateKeyExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("DuplicateKeyException error:", e);
        return Response.builder().code(CommonErrorDescEnum.DATABASE_UNIQUE_ERROR.getCode())
            .msg(CommonErrorDescEnum.DATABASE_UNIQUE_ERROR.getDesc()).build();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {

//        if (e instanceof BadSqlGrammarException) {
//            log.error("BadSqlGrammarException error:", e);
//            return Response.builder().code(ErrorCodeEnum.B_BAD_SQL_ERROR.getCode())
//                .msg(I18nHelper.getMessage(request, ErrorCodeEnum.B_BAD_SQL_ERROR))
//                .errorMsg(e.getCause().getMessage())
//                .build();
//        }

        // 递归拿到底层最真实的 exception
        Throwable t = e;
        while (true) {
            if (t != null && t.getCause() != null && t.getCause() instanceof InvocationTargetException) {
                Throwable throwable = ((InvocationTargetException) t.getCause()).getTargetException();
                if (throwable instanceof WmsException) {
                    return bizExceptionHandler(request, response, (WmsException) throwable);
                }
                t = throwable;
                continue;
            }
            break;
        }

        log.error("business catch exception error:", e);
        return Response.builder().code(CommonErrorDescEnum.SYSTEM_EXEC_ERROR.getCode())
            .msg(CommonErrorDescEnum.SYSTEM_EXEC_ERROR.getDesc()).build();
    }

    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalStateException.class, MissingServletRequestParameterException.class})
    public Response<Object> httpRequestExceptionHandler(HttpMessageNotReadableException exception) {
        log.error("http request error: ", exception);
        return Response.fail();
    }

    /**
     * 处理接口的数据检查异常
     */
//    @ResponseBody
//    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
//    public Response methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
//
//        String stackTraceAsString = Throwables.getStackTraceAsString(exception);
//        log.error("处理接口的数据检查异常: " + stackTraceAsString);
//
//        String message = null;
//
//        // 包装一下校验结果
//        List<Map<String, Object>> validResultMap = exception.getBindingResult().getFieldErrors().stream()
//            .map(v -> {
//                    Map<String, Object> fieldResultMap = new HashMap<>();
//                    fieldResultMap.put("Field", v.getField());
//                    fieldResultMap.put("rejectedValue", v.getRejectedValue());
//                    fieldResultMap.put("defaultMessage", v.getDefaultMessage());
//                    return fieldResultMap;
//                }
//            ).collect(Collectors.toList());
//
//        String errorFieldName = exception.getBindingResult().getFieldErrors().get(0).getField();
//        Class<?> clazz = exception.getParameter().getParameterType();
//        Field errorField = null;
//        while (clazz != null) {
//            try {
//                errorField = clazz.getDeclaredField(errorFieldName);
//                if (!errorField.isAnnotationPresent(ValidateErrorCode.class)) {
//                    errorField = null;
//                }
//            } catch (NoSuchFieldException ignored) {
//            }
//            clazz = clazz.getSuperclass();
//        }
//        if (errorField != null) {
//            ValidateErrorCode validateErrorCode = errorField.getAnnotation(ValidateErrorCode.class);
//            message = I18nHelper.getMessage(request, validateErrorCode.value(), validateErrorCode.args());
//        } else {
//            message = I18nHelper.getMessage(request, ErrorCodeEnum.A_PARAM_ERROR);
//        }
//
//        return Response.builder()
//            .code(CommonConstant.FAIL_CODE)
//            .msg(message)
//            .data(validResultMap)
//            .build();
//    }

}
