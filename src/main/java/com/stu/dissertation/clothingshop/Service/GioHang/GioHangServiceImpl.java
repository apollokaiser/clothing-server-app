package com.stu.dissertation.clothingshop.Service.GioHang;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.DTO.OutfitCartDTO;
import com.stu.dissertation.clothingshop.Entities.NguoiDung_GioHang;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.GioHangMapper;
import com.stu.dissertation.clothingshop.Mapper.TrangphucDetailMapper;
import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.GioHangRepository;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Deprecated(forRemoval = true)
public class GioHangServiceImpl implements GioHangService {
    private final GioHangRepository gioHangRepository;
    private final TrangPhucRepository trangPhucRepository;
    private final TrangphucDetailMapper trangphucDetailMapper;
    private final GioHangMapper gioHangMapper;

    @Override
    @Transactional
    public ResponseMessage getCarts(String id) {
        List<NguoiDung_GioHang> gioHangs = gioHangRepository.findByMaNguoiDung(id);
        if (gioHangs == null) return ResponseMessage.builder().status(OK).message("No cart").build();
        List<GioHangDTO> cartItems = gioHangMapper.convert(gioHangs);
        List<String> ids = cartItems.stream().map(GioHangDTO::getParentId).collect(Collectors.toList());
        List<TrangPhuc> trangPhucs = trangPhucRepository.getTrangPhucByIds(ids);
        List<OutfitCartDTO> cartDetails = trangPhucs.stream()
                .map(trangphucDetailMapper::convertToCartItem)
                .toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("Get carts successfully")
                .data(new HashMap<>() {{
                    put("cart_items", cartItems);
                    put("cart_details", cartDetails);
                }})
                .build();
    }

    @Override
    public void updateCarts(UpDateCartRequest cart) {
        try {
            gioHangRepository.updateCart(cart.getMaNguoiDung(), cart.getAddCart(), cart.getDeleteCart());
        } catch (Exception e) {
            throw new ApplicationException(BusinessErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        ResponseMessage.builder()
                .status(OK)
                .message("Delete cart successfully")
                .build();
    }
}
