package com.stu.dissertation.clothingshop.Security.EndPointFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Security.JWTAuth.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(1)
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    private List<String> PUBLIC_ENDPOINTS = List.of();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        if (isPublicEndpoint(request.getRequestURI())) {
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request) {
                @Override
                public String getHeader(String name) {
                    if (name.equals("Authorization")) {
                        return null;
                    }
                    return super.getHeader(name);
                }
            };
            chain.doFilter(wrapper, response);
        } else {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                ObjectMapper objectMapper = new ObjectMapper();
                ResponseMessage message = ResponseMessage.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Not full authentication jwtFilter")
                        .build();
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(message));
                return;
            }
            chain.doFilter(request, response);
            }
        }

    public void getPermitEnpoint(String[] endpoint) {
        PUBLIC_ENDPOINTS = Arrays.stream(endpoint)
                .map(ep -> ep.replace("**", ""))
                .collect(Collectors.toList());
    }
    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(path::contains) || path.contains("public");
    }
}
