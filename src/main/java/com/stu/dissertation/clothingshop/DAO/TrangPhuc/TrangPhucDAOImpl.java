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
    private final TrangPhucRepository trangPhucRepository;
    private final TrangPhucMapper trangPhucMapper;
    private final TrangphucDetailMapper trangphucDetailMapper;
    @Override
    @Transactional
    public List<TrangPhucDTO> getTrangPhuc(Pageable pageable) {
        List<TrangPhucDTO> trangPhucList = trangPhucRepository.findAll(pageable)
                .stream()
                .filter(trangphuc-> trangphuc.getTrangPhucChinhs().isEmpty())
                .map(trangPhucMapper::convert).toList();
        return trangPhucList;
    }

    @Override
    @Transactional
    public TrangPhucDetailDTO getTrangPhucDetails(String id) {
        TrangPhuc trangPhuc = trangPhucRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Trang phuc not found")
        );
        return trangphucDetailMapper.convert(trangPhuc);
//        return trangPhucRepository.getTrangPhucDetails(id);
    }

    @Override
    public List<TrangPhucDetailDTO> getTrangPhucInCart(List<String> ids) {
        List<TrangPhuc> trangPhucs = trangPhucRepository.getTrangPhucByIds(ids);
        return trangPhucs.stream().map(trangphucDetailMapper::convertToCartItem).toList();
    }

    @Override
    public Optional<TrangPhuc> findById(String id) {
        return trangPhucRepository.findById(id);
    }

    @Override
    public TrangPhuc save(TrangPhuc entity) {
        return trangPhucRepository.save(entity);
    }

    @Override
    public TrangPhuc update(TrangPhuc entity) {
        return trangPhucRepository.save(entity);

    }

    @Override
    public void delete(List<String> ids) {
        trangPhucRepository.deleteAllById(ids);
    }
}
