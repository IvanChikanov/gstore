package com.chikanov.gstore.enums;

public enum TypesOfMessage {
    ACTION("A"),
    AUTH("AU"),
    FINISH("F"),
    START("S"),
    ERROR("E"),
    RECONNECT("RE");
    private String string;
    TypesOfMessage(String type){
        string = type;
    }
    public String get(){
        return string;
    }

    public static TypesOfMessage getType(String text){
        for(TypesOfMessage tos : TypesOfMessage.values()){
            if(tos.get().equals(text)){
                return tos;
            }
        }
        throw new IllegalArgumentException(String.format("Тип %s не найден!", text));
    }
}
