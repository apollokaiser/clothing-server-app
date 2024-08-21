package com.stu.dissertation.clothingshop.Controller.Secured;

import com.stu.dissertation.clothingshop.Payload.Request.AddpromotionRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.KhuyenMai.KhuyenMaiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/secured/khuyen-mai")
@RequiredArgsConstructor
@Slf4j
public class KhuyenMaiController {
    private final KhuyenMaiService khuyenMaiService;
    private final HttpHeaders headers;

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
