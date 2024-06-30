package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Long> {
    @Query("SELECT km FROM KhuyenMai km WHERE km.ngayBatDau = 0 AND km.ngayKetThuc = 0 AND km.theLoais IS EMPTY")
    Set<KhuyenMai> getUnGroupPromotions();
}
