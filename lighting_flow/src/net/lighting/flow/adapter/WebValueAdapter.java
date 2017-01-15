package net.lighting.flow.adapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.lighting.flow.base.K;
import net.lighting.flow.base.ValueAdapter;

public class WebValueAdapter implements ValueAdapter {
    
    private ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();
    private ThreadLocal<HttpServletResponse> localResponse = new ThreadLocal<HttpServletResponse>();
    private ThreadLocal<HttpSession> localSession = new ThreadLocal<HttpSession>();
    
    public void init(HttpServletRequest request, HttpServletResponse response) {
        localRequest.set(request);
        localSession.set(request.getSession());
        localResponse.set(response);
    }

    @Override
    public Object get(String key) {
        if (key.startsWith(K.para_)) {
            return localRequest.get().getParameter(key.substring(K.para_.length()));
        }
        if (key.startsWith(K.request_)) {
            return localRequest.get().getAttribute(key.substring(K.request_.length()));
        }
        if (key.startsWith(K.session_)) {
            return localSession.get().getAttribute(key.substring(K.session_.length()));
        }
        if (key.startsWith(K.cookie_)) {
            Cookie[] cookies = localRequest.get().getCookies();
            String name = key.substring(K.cookie_.length());
            for (Cookie c : cookies) {
                if (name.equals(c.getName())) {
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public void set(String key, Object value) {
        if (key.startsWith(K.request_)) {
            localRequest.get().setAttribute(key.substring(K.request_.length()), value);
        }
        if (key.startsWith(K.session_)) {
            localSession.get().setAttribute(key.substring(K.session_.length()), value);
        }
        if (key.startsWith(K.cookie_)) {
            localResponse.get().addCookie((Cookie)value);
        }
    }
    
}
