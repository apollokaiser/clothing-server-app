package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @OneToMany(mappedBy = "theLoai", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<TrangPhuc> trangPhucs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maloai_parent", referencedColumnName = "maloai")
    @JsonBackReference
    private TheLoai parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TheLoai> children;

    public TheLoai(TheLoai theLoai) {
        this.maLoai = theLoai.getMaLoai();
        this.tenLoai = theLoai.getTenLoai();
        this.slug = theLoai.getSlug();
        this.parent = theLoai.getParent();
        this.children = theLoai.getChildren();
    }
}
