package com.stu.dissertation.clothingshop.Service.TrangPhuc;

import com.stu.dissertation.clothingshop.DAO.TrangPhuc.TrangPhucDAO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDetailDTO;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrangPhucServiceImpl implements TrangPhucService{
    private final TrangPhucDAO trangPhucDAO;
    @Override
    public ResponseMessage getTrangPhuc(Pageable pageable) {
        return ResponseMessage.builder()
                .status(HttpStatus.OK)
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
                .status(HttpStatus.OK)
                .message("Get data successfully")
                .data(new HashMap<>(){{
                    put("trangphucs",trangPhuc);
                }})
                .build();
    }
}
