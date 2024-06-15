package com.stu.dissertation.clothingshop.Service.TheLoai;


import com.stu.dissertation.clothingshop.DAO.TheLoai.TheLoaiDAO;
import com.stu.dissertation.clothingshop.Entities.TheLoai;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TheLoaiServiceImpl implements TheLoaiService{
    private final TheLoaiDAO theloaiDAO;
    @Override
    @Transactional
    public ResponseMessage getTheLoai() {
        List<TheLoai> list =theloaiDAO.getTheLoais();
        List<TheLoai> result = list.stream()
                .filter(theLoai -> theLoai.getParent()==null)
                .collect(Collectors.toList());
        return ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Get data successfully")
                .data(new HashMap<>(){{
                    put("theloais", result);
                }})
                .build();
    }

//    @Override
//    @Transactional
//    public List<TheLoai> buildHierarchy() {
//
//    }
}
