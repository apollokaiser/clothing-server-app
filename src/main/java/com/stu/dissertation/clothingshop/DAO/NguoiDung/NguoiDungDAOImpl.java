package com.stu.dissertation.clothingshop.DAO.NguoiDung;

import com.stu.dissertation.clothingshop.DTO.NguoiDungDetailDTO;
import com.stu.dissertation.clothingshop.Entities.DiaChi;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.Role;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.NguoiDungMapper;
import com.stu.dissertation.clothingshop.Repositories.DiaChiRepository;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Repositories.RoleRepository;
import com.stu.dissertation.clothingshop.Utils.CustomAnnotation.NotUse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class NguoiDungDAOImpl implements NguoiDungDAO{
    private final NguoiDungRepository nguoiDungRepository;
    private final RoleRepository roleRepository;
    private final NguoiDungMapper nguoiDungMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<NguoiDung> findById(String id) {
        return nguoiDungRepository.findById(id);
    }

    @Override
    public NguoiDung save(NguoiDung entity) {
        boolean isUser = nguoiDungRepository.findByEmail(entity.getEmail()).isPresent();
        if(isUser) throw new ApplicationException(BusinessErrorCode.USER_ALREADY_EXIST);
        Role role = roleRepository.findById("ROLE_USER")
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.ROLE_NOT_AVAILABLE));
        entity.setRoles(new HashSet<>(){{add(role);}});
        return nguoiDungRepository.save(entity);
    }
    @Override
    @Transactional
    public Optional<NguoiDung> findNguoiDungByEmail(String email) {
        return nguoiDungRepository.findByEmail(email);
    }
    @Override
    @Transactional
    public NguoiDungDetailDTO findUserById(String id) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND));
        return nguoiDungMapper.convert(nguoiDung);
    }
    @Override
    @Transactional
    public void resetPassword(String email, String newPassword) {
     NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
             .orElseThrow(()->new ApplicationException(BusinessErrorCode.USER_NOT_FOUND));
     if(passwordEncoder.matches(newPassword, nguoiDung.getMatKhau()))
         throw new ApplicationException(BusinessErrorCode.NO_CHANGE_APPLY, "Cannot set a same with old pasword");
     nguoiDung.setMatKhau(passwordEncoder.encode(newPassword));
     nguoiDungRepository.save(nguoiDung);
    }
}
