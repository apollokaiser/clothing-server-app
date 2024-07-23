package com.stu.dissertation.clothingshop.Config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth ==null || !auth.isAuthenticated()){
            return Optional.empty();
        }
        else{
            return Optional.of(auth.getName());
        }
    }
}
