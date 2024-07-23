package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, String> {
    @Query("SELECT nd FROM NguoiDung nd WHERE nd.email = ?1 AND " +
            "NOT EXISTS (SELECT 1 FROM NguoiDung nd2 WHERE nd2.adminEmail = ?1)")
    Optional<NguoiDung> findByEmail(String email);
    @Query("SELECT ng FROM NguoiDung ng WHERE ng.adminEmail = ?1")
    Optional<NguoiDung> findByAdminEmail(String email);
    @Query("SELECT u FROM NguoiDung u JOIN u.roles r WHERE r.role IN ?1")
    List<NguoiDung> findAdminsByRole(List<String> roleName, Pageable pageable);
    @Modifying
    @Query("UPDATE NguoiDung u SET u.lastLogin =?1 WHERE u.email = ?2 OR u.adminEmail = ?2")
    void updatelastLogin(Long time, String email);
}
