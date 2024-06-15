package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.TheLoai.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "danh-muc")
@RequiredArgsConstructor
public class TheLoaiController {
    private final HttpHeaders headers;
    private final TheLoaiService theLoaiService;
    @GetMapping(value = "/danh-sach-danh-muc")
    public ResponseEntity<?> getDanhMuc() {
        ResponseMessage response = theLoaiService.getTheLoai();
        return new ResponseEntity<>(response,headers,HttpStatus.OK);
    }
}
