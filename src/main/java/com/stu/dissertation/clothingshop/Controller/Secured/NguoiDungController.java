package com.stu.dissertation.clothingshop.Controller.Secured;

import com.stu.dissertation.clothingshop.Payload.Request.AddressRequest;
import com.stu.dissertation.clothingshop.Payload.Request.RePasswordRequest;
import com.stu.dissertation.clothingshop.Payload.Request.UpdateAddressRequest;
import com.stu.dissertation.clothingshop.Payload.Request.UpdateUserRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.DonThue.DonThueService;
import com.stu.dissertation.clothingshop.Service.NguoiDung.NguoiDungService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class NguoiDungController {
    private final NguoiDungService nguoiDungService;
    private final DonThueService donThueService;
    private final HttpHeaders headers;

    @GetMapping("/info")
    public ResponseEntity<ResponseMessage> info(@RequestParam("uid") String uid) {
       ResponseMessage response = nguoiDungService.getUserInfo(uid);
        return new ResponseEntity<>(response, headers, OK);
    }
    @PostMapping("/change-password")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody RePasswordRequest request) {
        ResponseMessage response = nguoiDungService.changePassword(request);
        return new ResponseEntity<>(response, headers, OK);
    }
    @PostMapping("change-info")
    public ResponseEntity<ResponseMessage> changeInfo(@RequestBody UpdateUserRequest update, HttpServletRequest request) {
        ResponseMessage response = nguoiDungService.changeInfo(update, request);
        return new ResponseEntity<>(response, headers, OK);
    }
    @PostMapping("add-address")
    public ResponseEntity<ResponseMessage> addAddress(@RequestBody AddressRequest address) {
        ResponseMessage response = nguoiDungService.addAddress(address);
        return new ResponseEntity<>(response, headers, OK);
    }
    @DeleteMapping("/delete-address")
    public ResponseEntity<ResponseMessage> deleteAddress(@RequestParam("id") Long id) {
        ResponseMessage response = nguoiDungService.deleteAddress(id);
        return new ResponseEntity<>(response, headers, OK);
    }
    @PostMapping("update-address")
    public ResponseEntity<ResponseMessage> updateAddress(@RequestBody UpdateAddressRequest request) {
        ResponseMessage response = nguoiDungService.updateAddress(request.address(),request.updateDefault());
        return new ResponseEntity<>(response, headers, OK);
    }
    @PutMapping("/set-default-address")
    public ResponseEntity<ResponseMessage> setDefaultAddress(Long id, boolean setDefault) {
        ResponseMessage response = nguoiDungService.updateAdress(id, setDefault);
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping("/get-orders")
    public ResponseEntity<ResponseMessage> getOrders(@RequestParam("uid") String uid) {
        ResponseMessage response = donThueService.getOrder(uid);
        return new ResponseEntity<>(response, headers, OK);
    }
    @GetMapping("/dang-xuat")
    @ResponseStatus(value = OK, reason = "logout successfully")
    public void logout(@RequestParam("token") String accessToken) throws ParseException {
        nguoiDungService.logout(accessToken);
    }
}
