package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.DonThue.DonThueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thanh-toan")
@RequiredArgsConstructor
public class ThanhToanController {
    private final DonThueService donThueService;
    private final HttpHeaders headers;

    @PostMapping(value = "/thanh-toan-khach-hang")
    public ResponseEntity<?> payment(@RequestBody @Valid OrderRequest orderDetail) {
        ResponseMessage response = donThueService.saveOrder(orderDetail);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @PostMapping(value="/thanh-toan-tien-loi")
    public ResponseEntity<?> paymentWithInterest(
            @RequestBody @Valid OrderRequest orderDetail,
            @RequestParam(value="sessionCode") String sessionCode) {
        ResponseMessage response = donThueService.saveOrderWithoutAccount(orderDetail, sessionCode);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

}
