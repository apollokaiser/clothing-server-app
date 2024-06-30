package com.stu.dissertation.clothingshop.DAO.DonThue;

import com.stu.dissertation.clothingshop.DAO.NguoiDung.NguoiDungDAO;
import com.stu.dissertation.clothingshop.Entities.DonThue;
import com.stu.dissertation.clothingshop.Repositories.DonThueRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DonThueDAOImpl implements DonThueDAO{
    private final DonThueRepository donThueRepository;
    private final NguoiDungDAO nguoiDungDAO;
    @Override
    public Optional<DonThue> findById(String id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public DonThue save(DonThue entity) {
        return donThueRepository.save(entity);
    }

    @Override
    public DonThue update(DonThue entity) {
        return null;
    }

    @Override
    public void delete(List<String> ids) {

    }
}
