package com.stu.dissertation.clothingshop.Service.GioHang;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;
import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;

import java.text.ParseException;
import java.util.List;


public interface GioHangService {
    ResponseMessage getCarts(String id);
   default void updateCarts(UpDateCartRequest cart){
       System.out.println("METHOD DEFAULT: ");
   }
   default void saveCart(GioHangDTO gioHangDTO) throws ParseException {
       System.out.println("METHOD DEFAULT: ");
   }
   default void clearCart(String id){
       System.out.println("METHOD DEFAULT: ");
   }
   default void updateCart( GioHangDTO gioHangDTO) throws ParseException {
       System.out.println("METHOD DEFAULT: ");
   }
    default void updateCart( GioHangDTO oldItem, GioHangDTO newItem) throws ParseException {
        System.out.println("METHOD DEFAULT: ");
    }
   default void deleteCart( GioHangDTO gioHangDTO) throws ParseException {
       System.out.println("METHOD DEFAULT: ");
   }
   default void deleteCart( List<GioHangDTO> gioHangDTOs) throws ParseException {
       System.out.println("METHOD DEFAULT: ");
   }

    default void prepareOrder(Cart cart) {

    }
}
