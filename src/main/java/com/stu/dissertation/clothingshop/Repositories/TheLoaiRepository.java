package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Long> {
    @Query("SELECT tl FROM TheLoai tl LEFT JOIN FETCH tl.children LEFT JOIN FETCH tl.parent")
    List<TheLoai> getTheLoai();
}
