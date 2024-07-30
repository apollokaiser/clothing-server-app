package com.stu.dissertation.clothingshop.DAO.TrangPhuc;

import com.stu.dissertation.clothingshop.DAO.GenericDAO;
import com.stu.dissertation.clothingshop.DTO.OutfitCartDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDetailDTO;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;

import java.util.List;

public interface TrangPhucDAO extends GenericDAO<TrangPhuc, String> {
   TrangPhucDetailDTO getTrangPhucDetails(String id);
   List<OutfitCartDTO> getTrangPhucInCart(List<String> ids);
}
