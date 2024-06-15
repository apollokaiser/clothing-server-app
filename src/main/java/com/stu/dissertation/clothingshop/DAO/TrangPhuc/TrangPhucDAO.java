package com.stu.dissertation.clothingshop.DAO.TrangPhuc;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDetailDTO;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TrangPhucDAO extends GenericDAO<TrangPhuc, String> {
    List<TrangPhucDTO> getTrangPhuc(Pageable pageable);
   TrangPhucDetailDTO getTrangPhucDetails(String id);
}
