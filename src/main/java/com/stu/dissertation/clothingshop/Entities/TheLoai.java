package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "theloai")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheLoai extends BaseEntity{
    @Id
    @Column(name = "maloai")
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long maLoai;
    @Column(name="tenloai", columnDefinition = "VARCHAR(100) NOT NULL")
    private String tenLoai;
    @Column(name="url",columnDefinition = "VARCHAR(100) NOT NULL")
    private String slug;
    @Column(name="moTa",columnDefinition = "TEXT")
    private String moTa;
    @Column(name="for_accessary", columnDefinition = "TINYINT(1)")
    private Boolean forAccessary;
    @OneToMany(mappedBy = "theLoai", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<TrangPhuc> trangPhucs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maloai_parent", referencedColumnName = "maloai")
    private TheLoai parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<TheLoai> children = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "theloai_khuyenmai",
        joinColumns = @JoinColumn(name = "maloai", referencedColumnName = "maloai"),
        inverseJoinColumns = @JoinColumn(name = "ma_khuyen_mai", referencedColumnName = "ma_khuyen_mai")
    )
    @JsonIgnore
    private Set<KhuyenMai> khuyenMais;
    public TheLoai(TheLoai theLoai) {
        this.maLoai = theLoai.getMaLoai();
        this.tenLoai = theLoai.getTenLoai();
        this.slug = theLoai.getSlug();
        this.parent = theLoai.getParent();
        this.children = theLoai.getChildren();
    }
    @PrePersist
    public void prePersist() {
        if (Objects.equals(this.tenLoai, "")) {
           throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE, "category name cannot be empty");
        }
    }
}
