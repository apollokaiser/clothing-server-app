package com.stu.dissertation.clothingshop.DAO.NguoiDung;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;

import java.util.Optional;

public interface NguoiDungDAO extends GenericDAO<NguoiDung, Long> {
    Optional<NguoiDung> findNguoiDungByEmail(String email);
    boolean isExistUser(String email);
    Optional<NguoiDung> findUserById(Long id);
//    void validatedUser(String email);
    void resetPassword(String email, String newPassword);
    void changePassword(String email, String newPassword);
//    Optional<NguoiDung> getUsersByRefreshToken(String token);
//    void saveRefreshToken(NguoiDung user, String refreshToken);
}
