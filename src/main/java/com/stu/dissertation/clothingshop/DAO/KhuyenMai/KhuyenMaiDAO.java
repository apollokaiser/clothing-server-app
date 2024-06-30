package com.stu.dissertation.clothingshop.DAO.KhuyenMai;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.DTO.KhuyenMaiThanhToanDTO;
import com.stu.dissertation.clothingshop.Entities.KhuyenMai;

import java.util.List;

public interface KhuyenMaiDAO extends GenericDAO<KhuyenMai,Long> {
    List<KhuyenMai> getKhuyenMais(Long id);
    List<KhuyenMai> getKhuyenMais();
    List<KhuyenMaiThanhToanDTO> getKhuyenMaisThanhToan();
}
