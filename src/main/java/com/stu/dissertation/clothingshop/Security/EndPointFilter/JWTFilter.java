package com.stu.dissertation.clothingshop.Security.EndPointFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JWTFilter extends OncePerRequestFilter {
    private List<String> PUBLIC_ENDPOINTS = List.of();
    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isPublicEndpoint(request.getRequestURI())){
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request){
                @Override
                public String getHeader(String name) {
                    if(name.equals("Authorization")){
                        return null;
                    }
                    return super.getHeader(name);
                }
            };
            filterChain.doFilter(wrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
    public void getPermitEnpoint(String[] endpoint) {
        PUBLIC_ENDPOINTS = Arrays.stream(endpoint)
                .map(ep->ep.replace("**", "")
                        .replace("/",""))
                .collect(Collectors.toList());
    }
    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(ep -> path.contains(ep));
    }
}
