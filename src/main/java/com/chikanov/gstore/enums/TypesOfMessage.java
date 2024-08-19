package com.chikanov.gstore.enums;

public enum TypesOfMessage {
    ACTION("A"),
    AUTH("AU"),
    FINISH("F"),
    START("S");
    private String string;
    TypesOfMessage(String type){
        string = type;
    }
    public String get(){
        return string;
    }
}
