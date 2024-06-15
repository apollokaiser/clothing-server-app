package com.stu.dissertation.clothingshop.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="role", indexes = @Index(name = "idx_role", columnList ="vai_tro"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity{
    @Id
    @Column(name="vai_tro")
    private String role;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<NguoiDung> nguoiDungs;
}
