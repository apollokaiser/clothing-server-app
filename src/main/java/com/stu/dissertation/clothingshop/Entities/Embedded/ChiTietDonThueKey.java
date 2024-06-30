package com.stu.dissertation.clothingshop.Entities.Embedded;

import com.stu.dissertation.clothingshop.Entities.DonThue;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChiTietDonThueKey implements Serializable {
    private String maChiTiet;
    private DonThue donThue;
    private TrangPhuc trangPhuc;
}
