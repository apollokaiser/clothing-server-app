package com.stu.dissertation.clothingshop.Entities.Embedded;

import com.stu.dissertation.clothingshop.Entities.DonThue;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class ChiTietDonThueKey implements Serializable {
    private String maDonThue;
    private TrangPhuc_KichThuocKey outfitSizeId;
}
