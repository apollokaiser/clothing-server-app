package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;
import com.stu.dissertation.clothingshop.Payload.Request.CartID;
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

import static org.springframework.http.HttpStatus.ACCEPTED;
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
    @PostMapping("/v2/xoa-gio-hang")
    @ResponseStatus(OK)
    public void deleteCart(@RequestBody @Valid GioHangDTO cart) throws ParseException {
        gioHangService.deleteCart(cart);
    }
    @PostMapping("/v2/cap-nhat-gio-hang")
    @ResponseStatus(OK)
    public void updateCart(@RequestBody GioHangDTO cart) throws ParseException {
        gioHangService.updateCart(cart);
    }
    @PostMapping("/v2/cap-nhat-trang-phuc-khac")
    @ResponseStatus(OK)
    public void updateCart(@RequestBody UpdateCart cart) throws ParseException {
        gioHangService.updateCart(cart.oldItem(), cart.newItem());
    }
    @PostMapping("/v2/prepared-order")
    @ResponseStatus(value=ACCEPTED, reason = "No unexpected ! Next step can continue")
    public void preparedOrder(@RequestBody @Valid Cart cart) throws ParseException {
        gioHangService.prepareOrder(cart);
    }
    @GetMapping("/v2/cancel-prepared-order")
    @ResponseStatus(value=ACCEPTED, reason = "No unexpected ! Next step can continue")
    public void cancelPreparedOrder() throws ParseException {
        gioHangService.cancelPreparedOrder();
    }
}
