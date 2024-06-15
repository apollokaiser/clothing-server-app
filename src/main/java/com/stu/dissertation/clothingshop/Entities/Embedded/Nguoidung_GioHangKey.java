package com.stu.dissertation.clothingshop.Entities.Embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
public class Nguoidung_GioHangKey implements Serializable {
    @Column(name = "ma_nguoi_dung")
    Long maNguoiDung;

    @Column(name = "ma_trang_phuc")
    String maTrangPhuc;
}
