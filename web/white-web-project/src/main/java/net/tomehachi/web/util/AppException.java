package net.tomehachi.web.util;

public class AppException extends Exception {

    public String appMessage;

    public AppException(String appMessage, Throwable e) {
        this.appMessage = appMessage;
        initCause(e);
    }
}
