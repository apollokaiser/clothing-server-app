package com.stu.dissertation.clothingshop.Config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationStarter {
    @Bean
    public ApplicationRunner appRunner() {
        return args -> {
            System.out.println("Application started ...");
        };
    }
}
