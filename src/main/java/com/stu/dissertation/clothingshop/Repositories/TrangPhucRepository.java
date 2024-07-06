package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TrangPhucRepository extends JpaRepository<TrangPhuc, String> {
    @Query("SELECT tp FROM TrangPhuc tp JOIN FETCH tp.hinhAnhs")
    List<TrangPhuc> getTrangPhuc();
    @Query("SELECT tp FROM TrangPhuc tp WHERE tp.id IN ?1")
    List<TrangPhuc> getTrangPhucByIds(List<String> ids);
    @Query("SELECT tp FROM TrangPhuc tp WHERE tp.theLoai = ?1")
    Set<TrangPhuc> findOutfitByCategory(String theLoai);
    @Query("SELECT tp FROM TrangPhuc tp JOIN tp.theLoai tl " +
            "WHERE LOWER(CONCAT(tp.tenTrangPhuc, ' ',tp.giaTien,' ', tp.moTa,' ',  tl.tenLoai, ' ', tl.slug)) " +
            "LIKE %?1%")
    Set<TrangPhuc> searchOutfit(String search);
}
