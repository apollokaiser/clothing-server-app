package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Service.GioHang.GioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class GioHangController {
    private final GioHangService gioHangService;
    @PostMapping("/update-carts")
    @ResponseStatus(HttpStatus.OK)
    public void updateCart(UpDateCartRequest cart) {
        gioHangService.updateCarts(cart);
    }
}
