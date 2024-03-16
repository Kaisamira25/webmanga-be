package com.alpha.lainovo.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieService {
    private final HttpServletResponse response;

    public Cookie add(String name, String value, int hours){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(hours);
        cookie.setPath("/");
        response.addCookie(cookie);
        return cookie;
    }
}
