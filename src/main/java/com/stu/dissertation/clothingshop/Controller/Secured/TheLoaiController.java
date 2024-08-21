package com.stu.dissertation.clothingshop.Controller.Secured;

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
@RequestMapping(value = "/secured/danh-muc")
@RequiredArgsConstructor
public class TheLoaiController {
    private final HttpHeaders headers;
    private final TheLoaiService theLoaiService;

    @PostMapping("/insert-category")
    public ResponseEntity<?> insertCategory(@RequestBody @Valid UpdateCategoryDTO theLoai) {
        ResponseMessage response = theLoaiService.insertCategory(theLoai);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @GetMapping("/delete-category")
    public ResponseEntity<?> deleteCategory(@RequestParam(value = "id") Long id,
                                            @RequestParam(value="deleteAll") boolean deleteAll) {
        ResponseMessage response = theLoaiService.deleteCategory(id, deleteAll);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @PostMapping("/update-category")
    public ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryDTO theLoai) {
        ResponseMessage response = theLoaiService.updateCategory(theLoai);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
