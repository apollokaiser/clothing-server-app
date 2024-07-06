package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.TheLoai.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
//    @GetMapping("/danh-sach-khuyen-mai-danh-muc")
//    public ResponseEntity<?> getDanhMucPromotion() {
//        ResponseMessage response = theLoaiService.getTheLoaiPromotion();
//        return new ResponseEntity<>(response,headers,HttpStatus.OK);
//    }
    @GetMapping("/danh-sach-trang-phuc")
    public ResponseEntity<?> getTrangPhucByCategory(Long category,
                                                    @RequestParam(value = "page",defaultValue = "0") int page,
                                                    @RequestParam(value="size", defaultValue = "10") int size) {
        ResponseMessage response = theLoaiService.getTrangPhucByCategory(category, page, size);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
