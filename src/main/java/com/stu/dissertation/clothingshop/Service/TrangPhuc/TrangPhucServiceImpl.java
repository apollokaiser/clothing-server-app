package com.stu.dissertation.clothingshop.Service.TrangPhuc;

import com.stu.dissertation.clothingshop.DAO.TrangPhuc.TrangPhucDAO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDetailDTO;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class TrangPhucServiceImpl implements TrangPhucService{
    private final TrangPhucDAO trangPhucDAO;
    @Override
    public ResponseMessage getTrangPhuc(Pageable pageable) {
        return ResponseMessage.builder()
                .status(OK)
                .message("Get data successfully")
                .data(new HashMap<>(){{
                    put("trangphucs", trangPhucDAO.getTrangPhuc(pageable));
                }})
                .build();
    }
    @Override
    @Transactional
    public ResponseMessage getTrangPhucDetails(String id) {
        TrangPhucDetailDTO trangPhuc =  trangPhucDAO.getTrangPhucDetails(id);
        return ResponseMessage.builder()
                .status(OK)
                .message("Get data successfully")
                .data(new HashMap<>(){{
                    put("trangPhuc",trangPhuc);
                }})
                .build();
    }

    @Override
    public ResponseMessage getTrangPhucInCart(List<String> ids) {
        List<TrangPhucDetailDTO> trangPhucs = trangPhucDAO.getTrangPhucInCart(ids);
       return ResponseMessage.builder()
                .status(OK)
                .message("Get cart detail data successfully")
                .data(new HashMap<>(){{
                    put("cart_details", trangPhucs);
                }})
                .build();
    }
}
