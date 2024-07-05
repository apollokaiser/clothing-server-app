package com.stu.dissertation.clothingshop.Service.TheLoai;


import com.stu.dissertation.clothingshop.DAO.TheLoai.TheLoaiDAO;
import com.stu.dissertation.clothingshop.DTO.TheLoaiDTO;
import com.stu.dissertation.clothingshop.DTO.TheLoaiPromotionDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Slf4j
public class TheLoaiServiceImpl implements TheLoaiService{
    private final TheLoaiDAO theloaiDAO;
    @Override
    @Transactional
    public ResponseMessage getTheLoai() {
        List<TheLoaiDTO> list =theloaiDAO.getTheLoais();
        return ResponseMessage.builder()
                .status(OK)
                .message("Get data successfully")
                .data(new HashMap<>(){{
                    put("theloais", list);
                }})
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage getTheLoaiPromotion() {
        List<TheLoaiPromotionDTO> theLoais = theloaiDAO.getTheLoaiHasPromotions();
        return ResponseMessage.builder()
                .status(OK)
                .message("Get promotion for categories successfully")
                .data(new HashMap<>(){{
                    put("theloai_promotions", theLoais);
                }})
                .build();
    }

    @Override
    public ResponseMessage getTrangPhucByCategory(Long category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<TrangPhucDTO> list = theloaiDAO.getTrangPhucByCategory(category, pageable);
        return ResponseMessage.builder()
                .status(OK)
                .message("Get data successfully")
                .data(new HashMap<>(){{
                    put("trangphucs", list);
                }})
                .build();

    }

//    @Override
//    @Transactional
//    public List<TheLoai> buildHierarchy() {
//
//    }
}
