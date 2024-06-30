package com.stu.dissertation.clothingshop.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NguoiDung extends BaseEntity implements UserDetails, Principal {
    @Id
    @Column(name="ma_nguoi_dung", columnDefinition = "VARCHAR(255)")
    String id;
    @Column(name="ten_nguoi_dung", columnDefinition = "VARCHAR(100)")
    String tenNguoiDung;
    @Column(name="email", columnDefinition = "VARCHAR(325) NOT NULL UNIQUE")
    String email;
    @Column(name="mat_khau", columnDefinition = "VARCHAR(100)")
    String matKhau;
    @Column(name="so_dien_thoai", columnDefinition = "VARCHAR(32)")
    String sdt;
    @Column(name="enabled", columnDefinition = "TINYINT(1) NOT NULL")
    Boolean enabled;
    @Column(name="khachMoi", columnDefinition = "TINYINT(1) NOT NULL")
    Boolean khachMoi;
    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE,
            orphanRemoval = true,
            mappedBy = "nguoiDung"
    )
    private RefreshToken refreshToken;
    @OneToOne(cascade = CascadeType.MERGE,
            orphanRemoval = true,
            mappedBy = "nguoiDung")
    private TaiKhoanLienKet taiKhoan;
    @OneToMany(mappedBy = "nguoiDung")
    Set<DiaChi> diaChis;
    @OneToMany(mappedBy = "nguoiDung", cascade = {CascadeType.PERSIST})
    Set<UserToken> userTokens;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name="ma_nguoi_dung", referencedColumnName = "ma_nguoi_dung"),
            inverseJoinColumns = @JoinColumn(name="vai_tro", referencedColumnName = "vai_tro")
    )
    Set<Role> roles;
    @OneToMany(mappedBy = "nguoiDung")
    Set<NguoiDung_GioHang> gioHangs;
    @OneToMany(mappedBy = "nguoiDung")
    Set<DonThue> donThues;
    @Override
    public String getName() {
        return this.tenNguoiDung;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role-> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.matKhau;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
