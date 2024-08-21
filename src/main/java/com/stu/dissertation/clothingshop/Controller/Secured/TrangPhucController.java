package com.stu.dissertation.clothingshop.Controller.Secured;

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
@RequestMapping("/secured/trang-phuc")
@RequiredArgsConstructor
public class TrangPhucController {
    private final HttpHeaders headers;
    private final TrangPhucService trangPhucService;

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
    @GetMapping("/ngung-cho-thue-kich-thuoc")
    public ResponseEntity<?> lockTrangPhucSize(@RequestParam("size") String size, @RequestParam("id") String id){
        ResponseMessage response = trangPhucService.lockOutfitSize(size,id);
        return new ResponseEntity<>(response,headers, OK);
    }
    @GetMapping("/mo-khoa-kich-thuoc")
    public ResponseEntity<?> unlockTrangPhucSize(@RequestParam("size") String size, @RequestParam("id") String id){
        ResponseMessage response = trangPhucService.unlockOutfitSize(size,id);
        return new ResponseEntity<>(response,headers, OK);
    }
    @GetMapping("/mo-khoa-trang-phuc")
    public ResponseEntity<?> unlockTrangPhuc(@RequestParam("id") String id){
        ResponseMessage response = trangPhucService.unlockTrangPhuc(id);
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
