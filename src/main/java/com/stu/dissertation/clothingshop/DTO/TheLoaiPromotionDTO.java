package com.stu.dissertation.clothingshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheLoaiPromotionDTO {
    private Long maLoai;
    private String tenLoai;
    private String slug;
    private KhuyenMaiDTO khuyenMai;
}
