package com.pfms.pfms_analytics.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String authHeader = request.getHeader("Authorization");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        try (PrintWriter writer = response.getWriter()) {
            if (authHeader == null || authHeader.isBlank()) {
                writer.write("{\"error\": \"Authorization header is missing\", \"message\": \"Please provide a valid Authorization token in the request header.\"}");
            } else {
                writer.write("{\"error\": \"Unauthorized access\", \"message\": \"Please provide valid credentials to access this resource.\"}");
            }
        }
    }
}



