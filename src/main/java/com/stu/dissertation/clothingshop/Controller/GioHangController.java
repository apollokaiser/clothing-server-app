package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.GioHang.GioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequiredArgsConstructor
@RequestMapping("/gio-hang")
public class GioHangController {
    private final GioHangService gioHangService;
    private final HttpHeaders headers;
    @PostMapping("/luu-gio-hang")
    @ResponseStatus(OK)
    public void updateCart(@RequestBody UpDateCartRequest cart) {
        gioHangService.updateCarts(cart);
    }
    @GetMapping("/danh-sach")
    public ResponseEntity<ResponseMessage> getCart(@RequestParam("uid") String id){
        ResponseMessage response = gioHangService.getCarts(id);
        return new ResponseEntity<>(response,headers,OK);
    }
}
