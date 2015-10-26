package controller.cookies_service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookiesService {
    public String getCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        String value = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("id")) {
                value = cookie.getValue();
                break;
            }
        }
        return value;
    }
}
