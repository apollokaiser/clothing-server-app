package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Payload.Request.UpDateCartRequest;
import com.stu.dissertation.clothingshop.Payload.Request.UpdateCart;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.GioHang.GioHangService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gio-hang/")
public class GioHangController {
    private final GioHangService gioHangService;
    private final HttpHeaders headers;
    @PostMapping("v1/luu-gio-hang")
    @ResponseStatus(OK)
    public void updateCart(@RequestBody UpDateCartRequest cart) {
        gioHangService.updateCarts(cart);
    }
    @Deprecated
    @GetMapping("v1/danh-sach")
    public ResponseEntity<ResponseMessage> getCarts(@RequestParam("uid") String id){
        ResponseMessage response = gioHangService.getCarts(id);
        return new ResponseEntity<>(response,headers,OK);
    }
    @PostMapping("/v2/luu-gio-hang")
    @ResponseStatus(OK)
    public void addCart(@RequestBody @Valid GioHangDTO cart) throws ParseException {
        gioHangService.saveCart(cart);
    }
    @GetMapping("/v2/danh-sach")
    @ResponseStatus(OK)
    public ResponseEntity<?> getCarts_v2(@RequestParam("indentify") String id) {
        ResponseMessage response =  gioHangService.getCarts(id);
        return new ResponseEntity<>(response, headers, OK);
    }
    @DeleteMapping("/v2/xoa-gio-hang")
    @ResponseStatus(OK)
    public void deleteCart( @RequestBody GioHangDTO cart) throws ParseException {
        gioHangService.deleteCart(cart);
    }
    @PutMapping("/v2/cap-nhat-gio-hang")
    @ResponseStatus(OK)
    public void updateCart( @RequestBody GioHangDTO cart) throws ParseException {
        gioHangService.updateCart(cart);
    }
    @PutMapping("/v2/cap-nhat-trang-phuc-khac")
    @ResponseStatus(OK)
    public void updateCart(@RequestBody UpdateCart cart) throws ParseException {
        gioHangService.updateCart(cart.oldItem(), cart.newItem());
    }
}
