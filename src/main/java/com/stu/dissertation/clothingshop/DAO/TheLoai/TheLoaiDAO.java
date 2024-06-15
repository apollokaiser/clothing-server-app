package com.stu.dissertation.clothingshop.DAO.TheLoai;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.Entities.TheLoai;

import java.util.List;

public interface TheLoaiDAO extends GenericDAO<TheLoai, Long> {
    List<TheLoai> getTheLoais();
}
