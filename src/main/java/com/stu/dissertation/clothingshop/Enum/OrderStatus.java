package com.stu.dissertation.clothingshop.Enum;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING(1,"Chờ nhận hàng"),
    DEPOSITED(2,"Đã đặt cọc"),
    DELIVERED(3,"Đã giao hàng"),
    CANCELED(4,"Đã hủy"),
    RETURNED(5,"Đã hoàn trả");

    private final int status;
    private final String description;
    OrderStatus(int status,String description) {
        this.status = status;
        this.description = description;
    }
}
