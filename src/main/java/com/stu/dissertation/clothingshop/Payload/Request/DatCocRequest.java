package com.stu.dissertation.clothingshop.Payload.Request;

public record DatCocRequest(
        Long soTien,
        Long ngayThu,
        String maDonThue,
        String trangThai,
        String payment

) {
}
