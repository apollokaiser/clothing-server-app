package com.stu.dissertation.clothingshop.Config;

import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.Role;
import com.stu.dissertation.clothingshop.Enum.ROLE;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import java.util.Arrays;
import java.util.HashSet;

@TestConfiguration
@Primary
public class TestSecurityConfig {
    private static final Role USER = Role.builder()
            .role(ROLE.USER.getRole())
            .build();
    private static final Role ADMIN = Role.builder()
            .role(ROLE.ADMIN.getRole())
            .build();
    private static final Role SUPER = Role.builder()
            .role(ROLE.HIGHEST_ROLE.getRole())
            .build();
    private static final Role MANAGER = Role.builder()
            .role(ROLE.MANAGER.getRole())
            .build();
    private static final Role OUTFIT_STAFF = Role.builder()
            .role(ROLE.OUTFIT_STAFF.getRole())
            .build();
    private static final Role PROMOTION_STAFF = Role.builder()
            .role(ROLE.PROMOTION_STAFF.getRole())
            .build();
    @Bean
    public UserDetailsService userDetailsService() {
        NguoiDung admin = NguoiDung.builder()
                .id("admin")
                .adminEmail("admin@gmail.com")
                .email("admin")
                .enabled(true)
                .matKhau("{noop}test")
                .roles(new HashSet<>(){{
                    addAll(Arrays.asList(USER,ADMIN));
                }})
                .build();
       NguoiDung manager = NguoiDung.builder()
               .id("admin_1")
               .adminEmail("admin@gmail.com")
               .email("manager")
               .enabled(true)
               .matKhau("{noop}test")
               .roles(new HashSet<>(){{
                   addAll(Arrays.asList(USER,ADMIN,MANAGER));
               }})
               .build();
       NguoiDung superAccount = NguoiDung.builder()
               .id("super_1")
               .adminEmail("super@gmail.com")
               .email("super")
               .enabled(true)
               .matKhau("{noop}test")
               .roles(new HashSet<>(){{
                   addAll(Arrays.asList(USER,SUPER,ADMIN));
               }})
               .build();
       NguoiDung outfitStaff = NguoiDung.builder()
               .id("outfit_1")
               .adminEmail("outfit1@gmail.com")
               .email("outfitStaff")
               .enabled(true)
               .matKhau("{noop}test")
               .roles(new HashSet<>(){{
                   addAll(Arrays.asList(USER,OUTFIT_STAFF,ADMIN));
               }})
               .build();
       NguoiDung promotionStaff = NguoiDung.builder()
               .id("promotion_1")
               .adminEmail("promotion1@gmail.com")
               .email("promotionStaff")
               .enabled(true)
               .matKhau("{noop}test")
               .roles(new HashSet<>(){{
                   addAll(Arrays.asList(USER,PROMOTION_STAFF,ADMIN));
               }})
               .build();
       NguoiDung user = NguoiDung.builder()
               .id("user")
               .email("user")
               .enabled(true)
               .matKhau("{noop}test")
               .roles(new HashSet<>(){{
                   add(USER);
               }})
               .build();
        return new InMemoryUserDetailsManager(admin,manager,superAccount,promotionStaff,outfitStaff, user);
    }
}
