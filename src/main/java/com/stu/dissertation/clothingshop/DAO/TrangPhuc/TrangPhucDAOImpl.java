package com.stu.dissertation.clothingshop.DAO.TrangPhuc;

import com.stu.dissertation.clothingshop.DTO.OutfitCartDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDetailDTO;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import com.stu.dissertation.clothingshop.Mapper.TrangphucDetailMapper;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class TrangPhucDAOImpl implements TrangPhucDAO {
    private final TrangPhucRepository trangPhucRepository;
    private final TrangphucDetailMapper trangphucDetailMapper;

    @Override
    @Transactional
    public TrangPhucDetailDTO getTrangPhucDetails(String id) {
        TrangPhuc trangPhuc = trangPhucRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Trang phuc not found")
        );
        return trangphucDetailMapper.convert(trangPhuc);
    }

    @Override
    @Transactional
    public List<OutfitCartDTO> getTrangPhucInCart(List<String> ids) {
        List<TrangPhuc> trangPhucs = trangPhucRepository.getTrangPhucByIds(ids);
        return trangPhucs.stream().map(trangphucDetailMapper::convertToCartItem).toList();
    }
}
