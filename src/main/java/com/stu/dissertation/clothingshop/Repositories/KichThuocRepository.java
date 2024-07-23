package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.KichThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface KichThuocRepository extends JpaRepository<KichThuoc, String> {
    @Query("SELECT kt FROM KichThuoc kt WHERE kt.id IN ?1")
    List<KichThuoc> getKichThuocByIds(List<String> kt);
}
