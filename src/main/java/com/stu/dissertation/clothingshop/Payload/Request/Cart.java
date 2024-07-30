package com.stu.dissertation.clothingshop.Payload.Request;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;

import java.util.List;

public record Cart(
        String identity,
        List<GioHangDTO> carts
) {
}
