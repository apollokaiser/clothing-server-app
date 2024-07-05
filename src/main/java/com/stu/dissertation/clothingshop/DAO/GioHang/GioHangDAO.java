package com.stu.dissertation.clothingshop.DAO.GioHang;
import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;

import java.util.Set;

public interface GioHangDAO {
    Set<GioHangDTO> getCart(String id);
    void updateCarts(UpDateCartRequest cart);
}
