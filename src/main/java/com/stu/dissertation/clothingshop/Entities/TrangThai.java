package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="trang_thai")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrangThai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ma_trang_thai", columnDefinition = "BIGINT NOT NULL")
    private Long maTrangThai;
    @Column(name="trang_thai", unique = true, columnDefinition = "VARCHAR(100) NOT NULL")
    private String trangThai;
    @Column(name="mo_ta", columnDefinition = "TEXT NOT NULL")
    private String moTa;
    @OneToMany(mappedBy="trangThai")
    @JsonIgnore
    private Set<DonThue> donThues;
}
