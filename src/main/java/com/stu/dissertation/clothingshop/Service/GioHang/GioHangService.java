package com.stu.dissertation.clothingshop.Service.GioHang;

import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;


public interface GioHangService {
    ResponseMessage getCarts(String id);
    ResponseMessage updateCarts(UpDateCartRequest cart);
}
