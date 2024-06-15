package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "kich_thuoc")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KichThuoc extends BaseEntity{
    @Id
    @Column(name="ma_kich_thuoc", columnDefinition = "CHAR(10) NOT NULL")
   private String id;
    @Column(name="mo_ta", columnDefinition = "VARCHAR(100)")
   private String moTa;
   @ManyToMany(mappedBy = "kichThuocs")
   @JsonIgnore
    private Set<TrangPhuc> trangPhucs;
}
