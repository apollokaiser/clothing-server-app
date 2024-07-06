package com.stu.dissertation.clothingshop.DAO.TheLoai;

import com.stu.dissertation.clothingshop.DTO.TheLoaiDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.Entities.TheLoai;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.TheLoaiMapper;
import com.stu.dissertation.clothingshop.Repositories.TheLoaiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TheLoaiDAOImpl implements TheLoaiDAO{
    private final TheLoaiRepository theLoaiRepository;
    private final TheLoaiMapper theLoaiMapper;

    @Override
    @Transactional
    public List<TheLoaiDTO> getTheLoais() {
        List<TheLoai> theLoais = theLoaiRepository.findAll();
        if(theLoais.isEmpty()) return null;
        return theLoais.stream()
                .filter(item->item.getParent()==null)
                .map(theLoaiMapper::convert)
                .collect(Collectors.toList());
    }

//    @Override
//    @Transactional
//    public List<TheLoaiPromotionDTO> getTheLoaiHasPromotions() {
//        List<TheLoai> theLoais = theLoaiRepository.getTheLoaiHasPromotions(Instant.now().getEpochSecond());
//        if(theLoais.isEmpty()) return null;
//        return theLoais.stream().map(theLoaiMapper::convertToGetPromotion).collect(Collectors.toList());
//    }

    @Override
    public List<TrangPhucDTO> getTrangPhucByCategory(Long category, Pageable pageable) {
        return null;
//        Set<TrangPhuc> trangPhucs = theLoaiRepository.getTrangPhucByCategory(category, pageable);
//        if(trangPhucs.isEmpty()) return null;
//        return trangPhucs.stream().map(trangPhucMapper::convert).collect(Collectors.toList());
    }

    @Override
    public Optional<TheLoai> findById(Long id) {
        return theLoaiRepository.findById(id);
    }

    @Override
    public TheLoai save(TheLoai entity) {
        return theLoaiRepository.save(entity);
    }

    @Override
    public TheLoai update(TheLoai entity) {
        TheLoai result = theLoaiRepository.findById(entity.getMaLoai())
                //TODO: add business error code
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.CONNECTION_FAILED));
        result.setTenLoai(entity.getTenLoai());
        return theLoaiRepository.save(result);
    }

    @Override
    public void delete(List<Long> ids) {
        theLoaiRepository.deleteAllById(ids);
    }
}
