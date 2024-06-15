package com.stu.dissertation.clothingshop.DAO.TheLoai;

import com.stu.dissertation.clothingshop.Entities.TheLoai;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Repositories.TheLoaiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class TheLoaiDAOImpl implements TheLoaiDAO{
    private final TheLoaiRepository repository;

    @Override
    @Transactional
    public List<TheLoai> getTheLoais() {
        return repository.getTheLoai();
    }

    @Override
    public Optional<TheLoai> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public TheLoai save(TheLoai entity) {
        return repository.save(entity);
    }

    @Override
    public TheLoai update(TheLoai entity) {
        TheLoai result = repository.findById(entity.getMaLoai())
                //TODO: add business error code
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.CONNECTION_FAILED));
        result.setTenLoai(entity.getTenLoai());
        return repository.save(result);

    }

    @Override
    public void delete(List<Long> ids) {
        repository.deleteAllById(ids);
    }
}
