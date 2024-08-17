package com.chikanov.gstore.enums;

public enum TypesOfMessage {
    ACTION("A"),
    AUTH("AU"),
    FINISH("F");
    private String string;
    TypesOfMessage(String type){
        string = type;
    }
}
