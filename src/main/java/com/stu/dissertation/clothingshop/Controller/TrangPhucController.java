package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.DTO.UpdateTrangPhucDTO;
import com.stu.dissertation.clothingshop.Payload.Request.CartID;
import com.stu.dissertation.clothingshop.Payload.Request.DeleteOutfit;
import com.stu.dissertation.clothingshop.Payload.Request.UpdateOutfit;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.TrangPhuc.TrangPhucService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/trang-phuc")
@RequiredArgsConstructor
public class TrangPhucController {
    private final HttpHeaders headers;
    private final TrangPhucService trangPhucService;

    @PostMapping("/public/get-init")
    public ResponseEntity<?> getInit(@RequestBody(required = false) @Valid CartID attention) {
        Pageable pageable = PageRequest.of(0,12);
        ResponseMessage response = trangPhucService.getInit(pageable, attention);
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping(value = "public/danh-sach")
    public ResponseEntity<?> getDSTrangPhuc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseMessage response = trangPhucService.getTrangPhuc(pageable);
        return new ResponseEntity<>(response, headers, OK);
    }
    @PostMapping(value="public/get-attention-outfit")
    public ResponseEntity<?> getAttentionOutfit(@RequestBody @Valid CartID attention) {
        ResponseMessage response = trangPhucService.getAttentionOutfit(attention.ids());
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping(value="public/get-lastest-outfit")
    public ResponseEntity<?> getLastestOutfit(@RequestParam(value="page", defaultValue = "0") int page,
                                               @RequestParam(value="size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        ResponseMessage response = trangPhucService.getLastestOutfit(pageable);
        return new ResponseEntity<>(response,headers, OK);
    }
    @GetMapping(value="public/chi-tiet-trang-phuc/{id}")
    public ResponseEntity<?> getTrangPhuc(@PathVariable(name="id") String id){
        ResponseMessage response = trangPhucService.getTrangPhucDetails(id);
        return new ResponseEntity<>(response,headers, OK);
    }
    @PostMapping("public/trang-phuc-gio-hang")
    public ResponseEntity<?> getTrangPhucInCart(@RequestBody CartID cartId){
        ResponseMessage response = trangPhucService.getTrangPhucInCart(cartId.ids());
        return new ResponseEntity<>(response,headers, OK);
    }
    @GetMapping("public/tim-kiem")
    public ResponseEntity<?> searchTrangPhuc(@RequestParam("keyword") String keyword,
                                             @RequestParam(value="page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        ResponseMessage response = trangPhucService.searchTrangPhuc(keyword, pageable);
        return new ResponseEntity<>(response,headers, OK);
    }
    @PostMapping("/them-trang-phuc")
    public ResponseEntity<?> addTrangPhuc(@RequestBody @Valid UpdateTrangPhucDTO trangPhuc){
        ResponseMessage response = trangPhucService.addTrangPhuc(trangPhuc);
        return new ResponseEntity<>(response,headers, OK);
    }
    @PostMapping("/ngung-cho-thue")
    public ResponseEntity<?> lockTrangPhuc(@RequestBody DeleteOutfit request){
        ResponseMessage response = trangPhucService.lockTrangPhuc(request.ids());
        return new ResponseEntity<>(response,headers, OK);
    }
    @PostMapping("/xoa-trang-phuc")
    public ResponseEntity<?> deleteTrangPhuc(@RequestBody DeleteOutfit request) {
        ResponseMessage response = trangPhucService.deleteTrangPhuc(request.ids());
        return new ResponseEntity<>(response,headers, OK);
    }
    @DeleteMapping("/xoa-trang-phuc")
    public ResponseEntity<?> deleteTrangPhuc(@RequestParam String id) {
        ResponseMessage response = trangPhucService.deleteTrangPhuc(id);
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping("/xem-thong-tin-trang-phuc")
    public ResponseEntity<?> getThongTinTrangPhuc(@RequestParam String id){
        ResponseMessage response = trangPhucService.getDetailUpdate(id);
        return new ResponseEntity<>(response,headers, OK);
    }
    @PutMapping("/cap-nhat-trang-phuc")
    public ResponseEntity<?> updateTrangPhuc(@RequestBody UpdateTrangPhucDTO trangPhuc){
        ResponseMessage response = trangPhucService.updateOutfit(trangPhuc);
        return new ResponseEntity<>(response,headers, OK);
    }
}
