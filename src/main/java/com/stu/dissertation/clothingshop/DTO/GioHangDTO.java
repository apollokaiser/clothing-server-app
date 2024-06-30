package com.stu.dissertation.clothingshop.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class GioHangDTO {
    private String id;
    private int quantity;
    private String size;
    private boolean full;
}
