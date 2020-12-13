package com.techqar.weblibrary.jsfui.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {

    public static final String COOKIE_LANG = "weblibrary-lang";

    public static void setCookie(String name, String value, int expiry) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] cookies = request.getCookies();
        if(null != cookies && cookies.length > 0) {
            for (Cookie item : cookies) {
                if (item.getName().equals(name)) {
                    cookie = item;
                    break;
                }
            }
        }

        if(null != cookie) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
        }

        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(expiry);

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);

    }

    public static Cookie getCookie(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] cookies = request.getCookies();
        if(null != cookies && cookies.length > 0) {
            for (Cookie item : cookies) {
                if (item.getName().equals(name)) {
                    cookie = item;
                    return cookie;
                }
            }
        }

        return null;
    }

}
