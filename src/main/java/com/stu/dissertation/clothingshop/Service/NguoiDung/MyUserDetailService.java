package com.stu.dissertation.clothingshop.Service.NguoiDung;

import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final NguoiDungRepository nguoiDungRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return nguoiDungRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Could not find user by username"));
    }
}
