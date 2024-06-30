package com.stu.dissertation.clothingshop.Repositories;

import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoanLienKet, String> {
    @Query("SELECT tk FROM TaiKhoanLienKet tk WHERE tk.nguoiDung.email = ?1")
    Optional<TaiKhoanLienKet> findAccountByEmail(String email);
}
