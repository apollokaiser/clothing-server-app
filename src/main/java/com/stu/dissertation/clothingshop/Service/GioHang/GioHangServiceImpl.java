package com.stu.dissertation.clothingshop.Service.GioHang;

import com.stu.dissertation.clothingshop.DAO.GioHang.GioHangDAO;
import com.stu.dissertation.clothingshop.DAO.TrangPhuc.TrangPhucDAO;
import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.DTO.OutfitCartDTO;
import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class GioHangServiceImpl implements GioHangService {
    private final GioHangDAO gioHangDAO;
    private final TrangPhucDAO trangPhucDAO;

    @Override
    @Transactional
    public ResponseMessage getCarts(String id) {
        Set<GioHangDTO> cartItems = gioHangDAO.getCart(id);
        if(cartItems == null) return ResponseMessage.builder().status(OK).message("No cart").build();
       List<String> ids = cartItems.stream().map(GioHangDTO::getParentId).collect(Collectors.toList());
        List<OutfitCartDTO> cartDetails =  trangPhucDAO.getTrangPhucInCart(ids);
        return ResponseMessage.builder()
               .status(OK)
               .message("Get carts successfully")
               .data(new HashMap<>(){{
                    put("cart_items", cartItems);
                    put("cart_details", cartDetails);
                }})
               .build();
    }
    @Override
    public void updateCarts(UpDateCartRequest cart) {
        gioHangDAO.updateCarts(cart);
        ResponseMessage.builder()
                .status(OK)
                .message("Delete cart successfully")
                .build();
    }
}
