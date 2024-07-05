package com.stu.dissertation.clothingshop.DAO.DiaChi;

import com.stu.dissertation.clothingshop.Entities.DiaChi;
import com.stu.dissertation.clothingshop.Repositories.DiaChiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DiaChiDAOImpl implements DiaChiDAO {
    private final DiaChiRepository diaChiRepository;
    @Override
    public Optional<DiaChi> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DiaChi save(DiaChi entity) {
        return null;
    }

    @Override
    public DiaChi update(DiaChi entity) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    @Transactional
    public void saveAll(List<DiaChi> diaChis) {
        saveAll(diaChis);
    }

    @Override
    public void updateAddress(List<DiaChi> diaChis) {

    }
}
