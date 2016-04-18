package com.lardi.phonebook.common;

import com.google.gson.Gson;
import com.lardi.phonebook.dto.LoginResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Nikolay Yashchenko
 */
public class PhoneBookAuthFailureHandler implements AuthenticationFailureHandler {

    private Gson gson = GsonHolder.gson;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.getWriter().write(gson.toJson(new LoginResponse(false)));
    }
}
