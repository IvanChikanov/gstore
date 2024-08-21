package com.chikanov.gstore.exceptions.enums;

public enum WsExceptionType {

    INVALID_JSON(10),
    DB_NOT_FOUND(11),
    AUTH_ERROR(12),
    ROOM_OVERLOAD(13),
    SESSION_ERROR(14);


    private final int code;

    WsExceptionType(int number){
        code = number;
    }

    public int getCode(){
        return code + 4000;
    }
}
