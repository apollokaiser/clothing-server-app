package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Long> {
    @Query("SELECT tl FROM TheLoai tl JOIN tl.khuyenMais km WHERE ?1 BETWEEN km.ngayBatDau AND km.ngayKetThuc")
    List<TheLoai> getTheLoaiHasPromotions(long currentTime);
    @Query(value = "SELECT tl FROM TheLoai tl WHERE tl.parent.maLoai = ?1")
    List<TheLoai> findByParentId(Long category);
}
