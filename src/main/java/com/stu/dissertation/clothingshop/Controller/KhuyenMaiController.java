package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.KhuyenMai.KhuyenMaiService;
import com.stu.dissertation.clothingshop.Service.PhieuKhuyenMai.PhieuKhuyenMaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/khuyen-mai")
@RequiredArgsConstructor
public class KhuyenMaiController {
    private final KhuyenMaiService khuyenMaiService;
    private final PhieuKhuyenMaiService phieuKhuyenMaiService;
    private final HttpHeaders headers;

    @GetMapping("/danh-sach-khuyen-mai-thanh-toan")
    public ResponseEntity<?> getPaymentPromotions() {
        ResponseMessage response = khuyenMaiService.getKhuyenMaiThanhToan();
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping("/check-code")
    public ResponseEntity<?> checkPromotionCode(@RequestParam("code") String code) {
        ResponseMessage response = phieuKhuyenMaiService.checkPhieuKhuyenMai(code);
        return new ResponseEntity<>(response, headers, OK);
    }
}
