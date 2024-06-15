package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrangPhucRepository extends JpaRepository<TrangPhuc, String> {
//   // @Query("SELECT DISTINCT tp FROM TrangPhuc tp LEFT JOIN FETCH tp.phuKien LEFT JOIN FETCH tp.hinhAnhs LEFT JOIN FETCH tp.mauSacs WHERE tp.id = ?1")
//    @EntityGraph(attributePaths = {"phuKien", "hinhAnhs", "mauSacs"})
//   @Query("SELECT tp FROM TrangPhuc tp WHERE tp.id = ?1")
//    TrangPhuc getTrangPhucDetails(String id);
    @Query("SELECT tp FROM TrangPhuc tp JOIN FETCH tp.hinhAnhs")
    List<TrangPhuc> getTrangPhuc();

}
