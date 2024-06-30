package com.stu.dissertation.clothingshop.Entities.Embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class TrangPhuc_KichThuocKey implements Serializable {
    @Column(name="ma_trang_phuc")
    private String maTrangPhuc;
    @Column(name="ma_kich_thuoc")
    private String maKichThuoc;
}
