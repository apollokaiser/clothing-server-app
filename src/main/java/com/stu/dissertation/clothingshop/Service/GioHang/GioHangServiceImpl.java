package com.stu.dissertation.clothingshop.Service.GioHang;

import com.stu.dissertation.clothingshop.DAO.GioHang.GioHangDAO;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;
import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
@Service
@RequiredArgsConstructor
public class GioHangServiceImpl implements GioHangService{

    private final GioHangDAO gioHangDAO;
    @Override
    public ResponseMessage updateCarts(UpDateCartRequest cart) {
        gioHangDAO.updateCarts(cart);
                 return ResponseMessage.builder()
                .status(OK)
                .message("Delete cart successfully")
                .build();
    }
}
