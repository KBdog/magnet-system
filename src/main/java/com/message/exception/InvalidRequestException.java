package com.message.exception;

import com.message.http.HttpEnum;

//自定义请求参数异常
public class InvalidRequestException extends RuntimeException{
    private int errCode;
    private String errMessage;

    public InvalidRequestException(HttpEnum httpEnum){
        this.errCode=httpEnum.code();
        this.errMessage=httpEnum.msg();
    }
    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
