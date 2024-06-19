package com.stu.dissertation.clothingshop.Payload.Request;

public record Cart(String maTrangPhuc,
                   int soluong,
                   String kichThuoc,
                   boolean isFull
                   ) {
}
