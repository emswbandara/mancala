package com.bol.mancala.exception;

public class GameException  extends Exception {

    private String errorCode;

    public GameException(String errorCode, String message) {

        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {

        return errorCode;
    }

    public void setErrorCode(String errorCode) {

        this.errorCode = errorCode;
    }
}
