package com.mm2.oauth.exception.handler;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionJSONInfo {

    @JsonProperty("error_code")
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
