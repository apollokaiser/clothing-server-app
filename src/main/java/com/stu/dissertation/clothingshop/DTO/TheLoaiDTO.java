package com.stu.dissertation.clothingshop.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TheLoaiDTO {
    private Long maLoai;
    private String tenLoai;
    private String slug;
    private String moTa;
    private Boolean forAccessary;
    private Long parentId;
    private List<TheLoaiDTO> children;
}
