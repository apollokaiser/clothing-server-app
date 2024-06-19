package com.stu.dissertation.clothingshop.DAO.GioHang;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;
import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;

import java.util.List;

public interface GioHangDAO {
    void updateCarts(UpDateCartRequest cart);
}
