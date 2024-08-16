package com.stu.dissertation.clothingshop.Cache.CacheService.GioHang;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;

import java.text.ParseException;
import java.util.List;


public interface GioHangRedisService {
    List<GioHangDTO> getCart(String id);
    void saveCart(String id, GioHangDTO gioHangDTO) throws ParseException;
    void deleteCart(String id, GioHangDTO gioHangDTO);
    void deleteCarts(String id, List<GioHangDTO> gioHangDTOs);
    void updateCart(String id, GioHangDTO gioHangDTO);
    void updateCart(String id, GioHangDTO oldItem, GioHangDTO newItem) throws ParseException;
    void clearCart(String id);
    void savePreOrder(String id, Cart cart);
    Cart getPreOrder(String id);
    void clearPreOrder(String id);
}
