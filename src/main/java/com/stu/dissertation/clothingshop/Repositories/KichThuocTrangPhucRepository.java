package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.Embedded.TrangPhuc_KichThuocKey;
import com.stu.dissertation.clothingshop.Entities.KichThuoc_TrangPhuc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KichThuocTrangPhucRepository extends JpaRepository<KichThuoc_TrangPhuc, TrangPhuc_KichThuocKey> {
}
