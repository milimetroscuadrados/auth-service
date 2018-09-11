package com.mm2.oauth.exception.handler;

import com.mm2.oauth.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //fixme el status de la exception no debería estar acá
    public @ResponseBody ExceptionJSONInfo handleServiceExceptions(HttpServletRequest request, ServiceException ex) {
        ExceptionJSONInfo exceptionJSONInfo = new ExceptionJSONInfo();
        exceptionJSONInfo.setErrorCode(ex.getErrorCode());

        return exceptionJSONInfo;
    }
}
