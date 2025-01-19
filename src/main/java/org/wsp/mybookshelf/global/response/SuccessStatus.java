package org.wsp.mybookshelf.global.response;

public enum SuccessStatus {
    _OK("200", "요청이 성공적으로 처리되었습니다.");

    private final String code;
    private final String message;

    SuccessStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}