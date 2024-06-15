package com.stu.dissertation.clothingshop.DAO.TrangPhuc;

import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDetailDTO;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import com.stu.dissertation.clothingshop.Mapper.TrangPhucMapper;
import com.stu.dissertation.clothingshop.Mapper.TrangphucDetailMapper;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class TrangPhucDAOImpl implements TrangPhucDAO {
    private final TrangPhucRepository repository;
    private final TrangPhucMapper trangPhucMapper;
    private final TrangphucDetailMapper trangphucDetailMapper;
    @Override
    @Transactional
    public List<TrangPhucDTO> getTrangPhuc(Pageable pageable) {
        List<TrangPhucDTO> trangPhucList = repository.findAll(pageable)
                .stream()
                .filter(trangphuc-> trangphuc.getTrangPhucChinhs().isEmpty())
                .map(trangPhucMapper::convert).toList();
        return trangPhucList;
    }

    @Override
    @Transactional
    public TrangPhucDetailDTO getTrangPhucDetails(String id) {
        TrangPhuc trangPhuc = repository.findById(id).orElseThrow(
                ()-> new RuntimeException("Trang phuc not found")
        );
        return trangphucDetailMapper.convert(trangPhuc);
//        return repository.getTrangPhucDetails(id);
    }

    @Override
    public Optional<TrangPhuc> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public TrangPhuc save(TrangPhuc entity) {
        return repository.save(entity);
    }

    @Override
    public TrangPhuc update(TrangPhuc entity) {
        return repository.save(entity);

    }

    @Override
    public void delete(List<String> ids) {
        repository.deleteAllById(ids);
    }
}
