package com.stu.dissertation.clothingshop.Payload.Request;

import java.math.BigDecimal;

public record DatCocRequest(
        Long soTien,
        Long ngayThu,
        String maDonThue,
        String trangThai,
        String payment,
        BigDecimal theChan

) {
}
