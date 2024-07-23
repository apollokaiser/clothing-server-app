package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.DTO.PhieuHoanTraDTO;
import com.stu.dissertation.clothingshop.Payload.Request.DatCocRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.DonThue.DonThueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/don-thue")
@PreAuthorize("hasRole('ADMIN') and " +
        "hasAnyAuthority('SUPER_ACCOUNT','FULL_CONTROL')")
@RequiredArgsConstructor
public class DonThueController {
    private final DonThueService donThueService;
    private final HttpHeaders headers;
    @GetMapping("/unconfirm-order-count")
    public ResponseEntity<ResponseMessage> unconfirmOrderCount(){  // 200 OK: success, 401 Unauthorized: not authorized, 500 Internal Server Error: error in server
        ResponseMessage response = donThueService.unconfirmOrderCount();
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @GetMapping("/get-don-thue")
    public ResponseEntity<ResponseMessage> getDonThue(
            @RequestParam(value="status") int status,
            @RequestParam(value="page", defaultValue = "1") int page,
            @RequestParam(value="size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseMessage response = donThueService.getOrders(pageable, status);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @GetMapping("change-status")
    public ResponseEntity<?> changeStatus(@RequestParam("id") String id,
                                        @RequestParam("status") Long status){
        ResponseMessage response = donThueService.changeStatus(id, status);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @GetMapping("/chi-tiet-don-thue")
    public ResponseEntity<?> getDonThueById(@RequestParam("id") String id){
        ResponseMessage response = donThueService.getOrderDetail(id);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @PostMapping("/dat-coc")
    public ResponseEntity<?> datCoc(@RequestBody @Valid DatCocRequest datCoc){
        ResponseMessage response = donThueService.addDeposit(datCoc);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @PostMapping("/xuat-phieu-hoan-tra")
    public ResponseEntity<?> printReturnBill(@RequestBody @Valid PhieuHoanTraDTO phieuHoanTra){
        ResponseMessage response = donThueService.printReturnBill(phieuHoanTra);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
