package com.stu.dissertation.clothingshop.DAO.DiaChi;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.Entities.DiaChi;

import java.util.List;

public interface DiaChiDAO extends GenericDAO<DiaChi, Long> {
    void saveAll(List<DiaChi> diaChis);
    void updateAddress(List<DiaChi> diaChis);
}
