package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DiaChiRepository extends JpaRepository<DiaChi, Long> {
    @Modifying
    @Query("UPDATE DiaChi d SET d.isDefault = false WHERE d.nguoiDung.id = ?1 AND d.isDefault = true")
    void resetDefaultAddress(String id);
}
