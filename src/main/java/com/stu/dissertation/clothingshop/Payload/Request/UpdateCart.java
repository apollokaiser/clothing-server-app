package com.stu.dissertation.clothingshop.Payload.Request;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;

public record UpdateCart(
        GioHangDTO oldItem,
        GioHangDTO newItem
) {
}
