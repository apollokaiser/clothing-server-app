package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, String> {
    @Query("SELECT ng FROM NguoiDung ng WHERE ng.email = ?1")
    Optional<NguoiDung> findByEmail(String email);
}
