package com.stu.dissertation.clothingshop.Config;

import com.stu.dissertation.clothingshop.Security.EndPointFilter.JWTAuthenticationEntryPoint;
import com.stu.dissertation.clothingshop.Security.EndPointFilter.JWTFilter;
import com.stu.dissertation.clothingshop.Security.JWTAuth.CustomJWTDecoder;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true,
        jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final AccessDeniedHandler accessDeniedHandler;
    private final JWTAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomJWTDecoder customJWTDecoder;
    private final JWTFilter jwtFilter;
    @NonFinal
    private final String[] PUBLIC_ENDPOINT = {
            "/auth/**",
            "/danh-muc/**",
            "/trang-phuc/**",
            "/khuyen-mai/danh-sach-khuyen-mai-thanh-toan",
            "/khuyen-mai/check-code",
            "/khuyen-mai/get-promotions",
            "/thanh-toan/thanh-toan-tien-loi"
    };
    @NonFinal
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        jwtFilter.getPermitEnpoint(PUBLIC_ENDPOINT);
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(req ->
                        req.requestMatchers(PUBLIC_ENDPOINT).permitAll()
                                .requestMatchers( "/user/**").hasRole("USER")
                                .requestMatchers("/gio-hang/**").hasRole("USER")
                                .requestMatchers("/thanh-toan/thanh-toan-khach-hang").hasRole("USER")
                                .anyRequest()
                                .authenticated()
//                                .requestMatchers(HttpMethod.POST, "/khuyen-mai/save-promotion").hasRole("ADMIN")
//                                .anyRequest()
//                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .accessDeniedHandler(this.accessDeniedHandler)
                     .authenticationEntryPoint(this.authenticationEntryPoint)
                );
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJWTDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                ).authenticationEntryPoint(this.authenticationEntryPoint)
        );
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantAuth = new JwtGrantedAuthoritiesConverter();
        grantAuth.setAuthorityPrefix("");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(grantAuth);
        return converter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of( "http://localhost:5173","**", ""));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
}
