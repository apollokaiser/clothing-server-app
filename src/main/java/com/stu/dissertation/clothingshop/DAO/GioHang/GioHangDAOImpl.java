package com.stu.dissertation.clothingshop.DAO.GioHang;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung_GioHang;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.GioHangMapper;
import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Repositories.GioHangRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GioHangDAOImpl implements GioHangDAO{

    private final GioHangRepository gioHangRepository;
    private final GioHangMapper gioHangMapper;

    @Override
    @Transactional
    public Set<GioHangDTO> getCart(Long id) {
      List<NguoiDung_GioHang> gioHangs =  gioHangRepository.findByMaNguoiDung(id);
      if(gioHangs.isEmpty()) return null;
      return gioHangs.stream().map(gioHangMapper::convert).collect(Collectors.toSet());
    }

    @Override
    public void updateCarts(UpDateCartRequest cart) {
        try {
        gioHangRepository.updateCart(cart.getMaNguoiDung(), cart.getAddCart(), cart.getDeleteCart());
        } catch (Exception e){
            throw new ApplicationException(BusinessErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}
