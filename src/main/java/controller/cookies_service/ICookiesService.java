package controller.cookies_service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by aminought on 27.10.2015.
 */
public interface ICookiesService {
    String getCookie(HttpServletRequest req, String name);
}
