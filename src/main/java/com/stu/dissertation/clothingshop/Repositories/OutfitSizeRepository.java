package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.Embedded.TrangPhuc_KichThuocKey;
import com.stu.dissertation.clothingshop.Entities.KichThuoc_TrangPhuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutfitSizeRepository extends JpaRepository<KichThuoc_TrangPhuc, TrangPhuc_KichThuocKey> {
    @Query("SELECT o FROM KichThuoc_TrangPhuc o WHERE o.id IN ?1")
    List<KichThuoc_TrangPhuc> getOutfitByIds(List<TrangPhuc_KichThuocKey> keys);
}
