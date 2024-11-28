package com.mindshare.cms.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mindshare.cmm.common.exception.message.ErrorMessageIF;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CmsErrorMessage implements ErrorMessageIF {
    BOARD_NOT_FOUND(400, "Board Not Found"),
    MENU_NOT_FOUND(400, "Menu Not Found");

    private int status;
    private String message;
    private String detail;
    private String uuid;

    CmsErrorMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

}
