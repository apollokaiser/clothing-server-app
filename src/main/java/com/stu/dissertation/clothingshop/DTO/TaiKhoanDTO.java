package com.stu.dissertation.clothingshop.DTO;

import com.stu.dissertation.clothingshop.Enum.LoginType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaiKhoanDTO {
    private String id;
    private LoginType provider;
}
