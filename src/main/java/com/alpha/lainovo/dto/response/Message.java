package com.alpha.lainovo.dto.response;

import lombok.Data;

@Data
public class Message {
    private int status;
    private String message;
    private Object data;

    public Message(int status, String message){
        this.status = status;
        this.message = message;
        this.data = "";
    }
    public Message(int status, String message, Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

