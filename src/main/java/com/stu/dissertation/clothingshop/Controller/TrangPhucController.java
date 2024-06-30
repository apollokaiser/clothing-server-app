package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Request.CartID;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.TrangPhuc.TrangPhucService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/trang-phuc")
@RequiredArgsConstructor
public class TrangPhucController {
    private final HttpHeaders headers;
    private final TrangPhucService trangPhucService;

    @GetMapping(value = "/danh-sach")
    public ResponseEntity<?> getDSTrangPhuc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseMessage response = trangPhucService.getTrangPhuc(pageable);
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping(value="/chi-tiet-trang-phuc/{id}")
    public ResponseEntity<?> getTrangPhuc(@PathVariable(name="id") String id){
        ResponseMessage response = trangPhucService.getTrangPhucDetails(id);
        return new ResponseEntity<>(response,headers, OK);
    }
    @PostMapping("/trang-phuc-gio-hang")
    public ResponseEntity<?> getTrangPhucInCart(@RequestBody CartID cartId){
        ResponseMessage response = trangPhucService.getTrangPhucInCart(cartId.ids());
        return new ResponseEntity<>(response,headers, OK);
    }
}
