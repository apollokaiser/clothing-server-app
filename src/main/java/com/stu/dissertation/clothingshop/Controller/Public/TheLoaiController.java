package com.stu.dissertation.clothingshop.Controller.Public;

import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.TheLoai.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/regular/danh-muc")
public class TheLoaiController {
    private final HttpHeaders headers;
    private final TheLoaiService theLoaiService;

    @GetMapping(value = "/danh-sach-danh-muc")
    public ResponseEntity<?> getCategoriesList() {
        ResponseMessage response = theLoaiService.getTheLoai();
        return new ResponseEntity<>(response, headers , OK);
    }
    @GetMapping("/danh-sach-trang-phuc")
    public ResponseEntity<?> getOutfitByCategory(
            @RequestParam(value = "id") Long category,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value="size", defaultValue = "10") int size) {
        ResponseMessage response = theLoaiService.getTrangPhucByCategory(category, page, size);
        return new ResponseEntity<>(response, headers, OK);
    }

}
