package com.stu.dissertation.clothingshop.DAO.TaiKhoanLienKet;

import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;
import com.stu.dissertation.clothingshop.Repositories.TaiKhoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaiKhoanLienKetDAOImpl implements TaiKhoanLienKetDAO{

    private final TaiKhoanRepository taiKhoanRepository;
    @Override
    public Optional<TaiKhoanLienKet> findById(String id) {
        return taiKhoanRepository.findById(id);
    }

    @Override
    public TaiKhoanLienKet save(TaiKhoanLienKet entity) {
        return null;
    }

    @Override
    public TaiKhoanLienKet update(TaiKhoanLienKet entity) {
        return null;
    }

    @Override
    public void delete(List<String> ids) {

    }

    @Override
    public Optional<TaiKhoanLienKet> findAccountEmail(String email) {
        return taiKhoanRepository.findAccountByEmail(email);
    }
}
