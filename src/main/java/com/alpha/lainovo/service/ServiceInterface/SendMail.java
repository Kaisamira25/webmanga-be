package com.alpha.lainovo.service.ServiceInterface;

public interface SendMail<T> {
    void sendMail(T content, String url);
}
