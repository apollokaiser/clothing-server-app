package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.DTO.UpdateCategoryDTO;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.TheLoai.TheLoaiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "danh-muc")
@RequiredArgsConstructor
public class TheLoaiController {
    private final HttpHeaders headers;
    private final TheLoaiService theLoaiService;
    @GetMapping(value = "/public/danh-sach-danh-muc")
    public ResponseEntity<?> getDanhMuc() {
        ResponseMessage response = theLoaiService.getTheLoai();
        return new ResponseEntity<>(response,headers,HttpStatus.OK);
    }
//    @GetMapping("/danh-sach-khuyen-mai-danh-muc")
//    public ResponseEntity<?> getDanhMucPromotion() {
//        ResponseMessage response = theLoaiService.getTheLoaiPromotion();
//        return new ResponseEntity<>(response,headers,HttpStatus.OK);
//    }
    @GetMapping("/public/danh-sach-trang-phuc")
    public ResponseEntity<?> getTrangPhucByCategory( @RequestParam(value = "id") Long category,
                                                    @RequestParam(value = "page",defaultValue = "0") int page,
                                                    @RequestParam(value="size", defaultValue = "10") int size) {
        ResponseMessage response = theLoaiService.getTrangPhucByCategory(category, page, size);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @PostMapping("/update/insert-category")
    public ResponseEntity<?> insertCategory(@RequestBody @Valid UpdateCategoryDTO theLoai) {
        ResponseMessage response = theLoaiService.insertCategory(theLoai);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @GetMapping("/update/delete-category")
    public ResponseEntity<?> deleteCategory(@RequestParam(value = "id") Long id,
                                            @RequestParam(value="deleteAll") boolean deleteAll) {
        ResponseMessage response = theLoaiService.deleteCategory(id, deleteAll);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @PostMapping("/update/update-category")
    public ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryDTO theLoai) {
        ResponseMessage response = theLoaiService.updateCategory(theLoai);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
