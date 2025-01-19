package org.wsp.mybookshelf.global.response;

public enum ReasonHttpStatus {
    OK("200", "요청이 성공적으로 처리되었습니다."),
    BAD_REQUEST("400", "잘못된 요청입니다."),
    NOT_FOUND("404", "찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("500", "서버 오류입니다.");

    private final String code;
    private final String message;

    ReasonHttpStatus(String code, String message) {
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