package com.lardi.phonebook.common;

import com.google.gson.Gson;
import com.lardi.phonebook.dto.LoginResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Nikolay Yashchenko
 */
public class PhoneBookAuthSuccessHandler implements AuthenticationSuccessHandler {

    private Gson gson = GsonHolder.gson;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        httpServletResponse.getWriter().write(gson.toJson(new LoginResponse(true)));
    }
}
