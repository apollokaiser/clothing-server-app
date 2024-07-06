package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Request.AddpromotionRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.KhuyenMai.KhuyenMaiService;
import com.stu.dissertation.clothingshop.Service.PhieuKhuyenMai.PhieuKhuyenMaiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/khuyen-mai")
@RequiredArgsConstructor
@Slf4j
public class KhuyenMaiController {
    private final KhuyenMaiService khuyenMaiService;
    private final PhieuKhuyenMaiService phieuKhuyenMaiService;
    private final HttpHeaders headers;

    @GetMapping("/danh-sach-khuyen-mai-thanh-toan")
    public ResponseEntity<?> getPaymentPromotions() {
        ResponseMessage response = khuyenMaiService.getKhuyenMaiThanhToan();
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping("/danh-sach-khuyen-mai-danh-muc")
    public  ResponseEntity<?> getPromotionCategory() {
        ResponseMessage response = khuyenMaiService.getPromotionsCategory();
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping("/check-code")
    public ResponseEntity<?> checkPromotionCode(@RequestParam("code") String code) {
        ResponseMessage response = phieuKhuyenMaiService.checkPhieuKhuyenMai(code);
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping("/get-promotions")
    public ResponseEntity<?> getPromotionList() {
        ResponseMessage response = khuyenMaiService.getPromotionList();
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping("/get-category-in-promotion")
    public ResponseEntity<?> getCategoryInPromotion(@RequestParam Long id) {
        ResponseMessage response = khuyenMaiService.getCategoryInPromotion(id);
        return new ResponseEntity<>(response, headers, OK);
    }
    @PostMapping("/save-promotion")
    public ResponseEntity<?> savePromotion(@RequestBody AddpromotionRequest promotion) {
      ResponseMessage response = khuyenMaiService.savePromotion(promotion);
        return new ResponseEntity<>(response, headers, OK);
    }
    @DeleteMapping("/delete-promotion")
    public ResponseEntity<?> deletePromotion(@RequestParam Long id) {
        ResponseMessage response = khuyenMaiService.deletePromotion(id);
        return new ResponseEntity<>(response, headers, OK);
    }
    @PutMapping("/update-promotion")
    public ResponseEntity<?> updatePromotion(@RequestBody AddpromotionRequest promotion) {
        ResponseMessage response = khuyenMaiService.updatePromotion(promotion);
        return new ResponseEntity<>(response, headers, OK);
    }
}
