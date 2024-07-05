package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.Entities.TheLoai;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Long> {
    @Query("SELECT tl FROM TheLoai tl JOIN tl.khuyenMais km WHERE :currentTime BETWEEN km.ngayBatDau AND km.ngayKetThuc")
    List<TheLoai> getTheLoaiHasPromotions(@Param("currentTime") long currentTime);
//    @Query(value = "SELECT tl.trangPhuc FROM TheLoai tl WHERE tl.maLoai = :category")
//    Set<TrangPhuc> getTrangPhucByCategory(@Param("category") Long category, Pageable pageable)
}
