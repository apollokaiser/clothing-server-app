package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Long> {
    @Query("SELECT tl FROM TheLoai tl JOIN tl.khuyenMais km WHERE :currentTime BETWEEN km.ngayBatDau AND km.ngayKetThuc")
    List<TheLoai> getTheLoaiHasPromotions(@Param("currentTime") long currentTime);
}
