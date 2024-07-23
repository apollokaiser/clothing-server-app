package com.stu.dissertation.clothingshop.Payload.Request;

public record UpdateAdminInfo(
        String id,
        String tenNguoiDung,
        String email,
        String sdt
) {
}
