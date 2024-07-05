package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.DonThue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface DonThueRepository extends JpaRepository<DonThue, String> {
    @Query("SELECT dt FROM DonThue dt WHERE dt.nguoiDung.id=?1")
    Set<DonThue> getDonThueByUID(String uid);
}
