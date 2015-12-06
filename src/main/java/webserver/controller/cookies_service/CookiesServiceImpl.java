package webserver.controller.cookies_service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookiesServiceImpl implements ICookiesService {
    @Override
    public String getCookie(HttpServletRequest req, String name) {
        String value = null;
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }
}
