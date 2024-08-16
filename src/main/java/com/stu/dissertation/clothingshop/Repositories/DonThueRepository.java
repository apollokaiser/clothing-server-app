package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.DonThue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DonThueRepository extends JpaRepository<DonThue, String> {
    @Query("SELECT dt FROM DonThue dt WHERE dt.nguoiDung.id=?1")
    Set<DonThue> getDonThueByUID(String uid);
    @Query("SELECT dt FROM DonThue dt WHERE dt.trangThai.maTrangThai=?1 ORDER BY dt.ngayThue DESC")
    List<DonThue> getDonThue(int status, Pageable pageable);
    @Query("SELECT COUNT(dt) FROM DonThue dt WHERE dt.trangThai.maTrangThai=?1")
    int getOrdersCount(int status);
    @Query("SELECT dt FROM DonThue dt " +
            "WHERE LOWER(CONCAT(dt.maDonThue, ' ',dt.tongThue,' ', dt.tenNguoiNhan,' ',  dt.sdtNguoiNhan, ' ', dt.diaChiNguoiNhan)) " +
            "LIKE %?1% ORDER BY dt.ngayThue DESC")
    List<DonThue> searchOrder(String keyword);
}
