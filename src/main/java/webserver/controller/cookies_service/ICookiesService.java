package webserver.controller.cookies_service;

import javax.servlet.http.HttpServletRequest;

public interface ICookiesService {
    String getCookie(HttpServletRequest req, String name);
}
