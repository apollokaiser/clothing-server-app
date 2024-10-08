package com.stu.dissertation.clothingshop.Payload.Request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpDateCartRequest {
    String maNguoiDung;
    String addCart;
    String deleteCart;
}
