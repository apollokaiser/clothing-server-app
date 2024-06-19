package com.stu.dissertation.clothingshop.Security.EndPointFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
public class AccessDeniHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            log.warn("Header :" + request.getHeader("Authorization"));
            log.warn("Authentication block access denied: " + auth.getAuthorities().toString());
            log.warn("User: {} attempted to access the protected URL: {}", auth.getName(), request.getRequestURI());
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}
