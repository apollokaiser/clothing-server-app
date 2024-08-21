package com.stu.dissertation.clothingshop.Controller.Public;

import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.KhuyenMai.KhuyenMaiService;
import com.stu.dissertation.clothingshop.Service.PhieuKhuyenMai.PhieuKhuyenMaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/regular/khuyen-mai")
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
    @GetMapping("/tat-ca-danh-sach-khuyen-mai")
    public ResponseEntity<?> getAllPromotions(@RequestParam(value="page", defaultValue = "0") int page,
                                              @RequestParam(value="size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseMessage response = khuyenMaiService.getAllPromotion(pageable);
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

}
