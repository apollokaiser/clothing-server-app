package com.stu.dissertation.clothingshop.Config;

import com.stu.dissertation.clothingshop.Entities.KichThuoc;
import com.stu.dissertation.clothingshop.Entities.Role;
import com.stu.dissertation.clothingshop.Entities.TheLoai;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Repositories.KichThuocRepository;
import com.stu.dissertation.clothingshop.Repositories.RoleRepository;
import com.stu.dissertation.clothingshop.Repositories.TheLoaiRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;


@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationStarter {
    final DataSource dataSource;
    final KichThuocRepository kichThuocRepository;
    final RoleRepository roleRepository;
    @NonFinal
    final Set<KichThuoc> kichThuocs = new HashSet<>(){{
            add(KichThuoc.builder().id("S").build());
            add(KichThuoc.builder().id("M").build());
            add(KichThuoc.builder().id("L").build());
            add(KichThuoc.builder().id("XL").build());
            add(KichThuoc.builder().id("XXL").build());
            add(KichThuoc.builder().id("XXXL").build());
            add(KichThuoc.builder().id("XXXXL").build());
            add(KichThuoc.builder().id("XXXXXL").build());
            add(KichThuoc.builder().id("XXXXXXL").build());
    }}  ;
    final Set<Role> roles = new HashSet<>(){{
       add(Role.builder().role("ROLE_ADMIN").build());
       add(Role.builder().role("ROLE_USER").build());
    }};
    @Bean
    public ApplicationRunner appRunner(){
        return args -> {
            System.out.println("Application started ...");
            if(roleRepository.count() == 0)
                roleRepository.saveAll(roles);
            if(kichThuocRepository.count() == 0)
                kichThuocRepository.saveAll(kichThuocs);
        };
    }
}
