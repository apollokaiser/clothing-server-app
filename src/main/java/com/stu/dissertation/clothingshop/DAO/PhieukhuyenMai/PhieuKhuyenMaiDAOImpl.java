package com.stu.dissertation.clothingshop.DAO.PhieukhuyenMai;

import com.stu.dissertation.clothingshop.Entities.PhieuKhuyenMai;
import com.stu.dissertation.clothingshop.Repositories.PhieuKhuyenMaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PhieuKhuyenMaiDAOImpl implements PhieuKhuyenMaiDAO{

    private final PhieuKhuyenMaiRepository phieuKhuyenMaiRepository;
    @Override
    public Optional<PhieuKhuyenMai> findById(String id) {
        return phieuKhuyenMaiRepository.findById(id);
    }

    @Override
    public PhieuKhuyenMai save(PhieuKhuyenMai entity) {
        return null;
    }

    @Override
    public PhieuKhuyenMai update(PhieuKhuyenMai entity) {
        return null;
    }

    @Override
    public void delete(List<String> ids) {

    }
}
