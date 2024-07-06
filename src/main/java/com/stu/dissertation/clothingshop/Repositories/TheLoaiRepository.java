package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Long> {
    @Query("SELECT tl FROM TheLoai tl JOIN tl.khuyenMais km WHERE ?1 BETWEEN km.ngayBatDau AND km.ngayKetThuc")
    List<TheLoai> getTheLoaiHasPromotions(long currentTime);
//    @Query(value = "SELECT tl.trangPhuc FROM TheLoai tl WHERE tl.maLoai = :category")
//    Set<TrangPhuc> getTrangPhucByCategory(@Param("category") Long category, Pageable pageable)
}
