package com.stu.dissertation.clothingshop.DAO.NguoiDung;

import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.Role;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NguoiDungDAOImpl implements NguoiDungDAO{
    private final NguoiDungRepository nguoiDungRepository;
    private final RoleRepository roleRepository;
    @Override
    public Optional<NguoiDung> findById(Long id) {
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
    public NguoiDung update(NguoiDung entity) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public Optional<NguoiDung> findNguoiDungByEmail(String email) {
        return nguoiDungRepository.findByEmail(email);
    }

    @Override
    public boolean isExistUser(String email) {
        return nguoiDungRepository.findByEmail(email).isPresent();
    }

    @Override
    public Optional<NguoiDung> findUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public void resetPassword(String email, String newPassword) {

    }

    @Override
    public void changePassword(String email, String newPassword) {

    }

//    @Override
//    public Optional<NguoiDung> getUsersByRefreshToken(String token) {
//        return Optional.empty();
//    }
//
//    @Override
//    public void saveRefreshToken(NguoiDung user, String refreshToken) {
//
//    }
}
