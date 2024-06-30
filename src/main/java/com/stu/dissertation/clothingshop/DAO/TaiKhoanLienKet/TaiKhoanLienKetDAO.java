package com.stu.dissertation.clothingshop.DAO.TaiKhoanLienKet;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;

import java.util.Optional;

public interface TaiKhoanLienKetDAO extends GenericDAO<TaiKhoanLienKet, String> {
    Optional<TaiKhoanLienKet> findById(String id);
    Optional<TaiKhoanLienKet> findAccountEmail(String email);
}
