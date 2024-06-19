package com.stu.dissertation.clothingshop.DAO.GioHang;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;
import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Repositories.GioHangRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GioHangDAOImpl implements GioHangDAO{

    private final GioHangRepository gioHangRepository;

    @Override
    public void updateCarts(UpDateCartRequest cart) {
        try {
        gioHangRepository.updateCart(cart.getMaNguoiDung(), cart.getAddCart(), cart.getDeleteCart());
        } catch (Exception e){
            throw new ApplicationException(BusinessErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}
