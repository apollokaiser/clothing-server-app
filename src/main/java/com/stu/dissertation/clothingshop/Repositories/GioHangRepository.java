package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.Embedded.Nguoidung_GioHangKey;
import com.stu.dissertation.clothingshop.Entities.NguoiDung_GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GioHangRepository
        extends JpaRepository<NguoiDung_GioHang, Nguoidung_GioHangKey> {
    @Query("SELECT ng FROM NguoiDung_GioHang ng WHERE ng.id.maNguoiDung =?1")
    List<NguoiDung_GioHang> findByMaNguoiDung(Long maNguoiDung);
    @Procedure(name = "PROC_add_cart")
    void updateCart(@Param("ma_nguoi_dung") Long maNguoiDung,
                    @Param("add_list") String addCart,
                    @Param("delete_list") String deleteCart);
}
