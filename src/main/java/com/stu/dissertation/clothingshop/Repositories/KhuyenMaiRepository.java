package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Long> {
    @Query("SELECT km FROM KhuyenMai km WHERE km.ngayBatDau = 0 AND km.ngayKetThuc = 0 AND km.theLoais IS EMPTY")
    Set<KhuyenMai> getUnGroupPromotions();
    @Procedure(name = "PROC_add_promotion")
    int addPromotion(@Param("promotion_id") Long promotionId,
                      @Param("category_id") Long categoryId,
                      @Param("this_time") Long thisTime);
    @Query("SELECT km FROM KhuyenMai km WHERE km.tenKhuyenMai = ?1")
    Optional<KhuyenMai> findKhuyenMaiByTenKhuyenMai(String name);
}
