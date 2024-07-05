package com.stu.dissertation.clothingshop.DAO.NguoiDung;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.DTO.NguoiDungDetailDTO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;

import java.util.Optional;

public interface NguoiDungDAO extends GenericDAO<NguoiDung, String> {
    Optional<NguoiDung> findNguoiDungByEmail(String email);
    NguoiDungDetailDTO findUserById(String id);
    void resetPassword(String email, String newPassword);
}
