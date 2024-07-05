package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="address_detail")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDetail {
    @Id
    Long id;
    @Column(name="province_id", nullable = false)
    int provinceId;
    @Column(name="district_id", nullable = false)
    int districtId;
    @Column(name="ward_id", nullable = false)
    int wardId;
    @OneToOne
    @MapsId
    @PrimaryKeyJoinColumn(name="ma_dia_chi")
    @JsonIgnore
    DiaChi diaChi;
}
