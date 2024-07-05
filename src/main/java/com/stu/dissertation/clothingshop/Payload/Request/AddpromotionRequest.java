package com.stu.dissertation.clothingshop.Payload.Request;

import com.stu.dissertation.clothingshop.DTO.KhuyenMaiDTO;

import java.util.List;

public record AddpromotionRequest(
        KhuyenMaiDTO khuyenMai,
        List<Long> ids
) {
}
