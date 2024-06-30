package com.stu.dissertation.clothingshop.Entities.Embedded;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Nguoidung_GioHangKey implements Serializable {
    Long gioHangId;
    String maNguoiDung;
    String maTrangPhuc;
}
