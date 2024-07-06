package com.stu.dissertation.clothingshop.DAO.TheLoai;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.DTO.TheLoaiDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.Entities.TheLoai;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TheLoaiDAO extends GenericDAO<TheLoai, Long> {
    List<TheLoaiDTO> getTheLoais();
//    List<TheLoaiPromotionDTO> getTheLoaiHasPromotions();
    List<TrangPhucDTO> getTrangPhucByCategory(Long category, Pageable pageable);
}
