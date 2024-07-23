package com.stu.dissertation.clothingshop.Controller;

import com.stu.dissertation.clothingshop.Payload.Request.AdminInfo;
import com.stu.dissertation.clothingshop.Payload.Request.ChangeRoleRequest;
import com.stu.dissertation.clothingshop.Payload.Request.UpdateAdminInfo;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Service.Staff.StaffService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/admin/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    private final HttpHeaders headers;
    @PostMapping("/add-staff")
    public ResponseEntity<?> addStaff(@RequestBody @Valid AdminInfo info) throws MessagingException {
        ResponseMessage message = staffService.addStaff(info);
        return new ResponseEntity<>(message, headers,OK);
    }
    @GetMapping("/lock-staff")
    public ResponseEntity<?> lockStaff(@RequestParam String  id){
       ResponseMessage message = staffService.lockStaff(id);
        return new ResponseEntity<>(message, headers,OK);
    }
    @PostMapping("/update-staff")
    public ResponseEntity<?> updateStaff(@RequestBody @Valid UpdateAdminInfo info){
        ResponseMessage message = staffService.updateStaff(info);
        return new ResponseEntity<>(message, headers,OK);
    }
    @GetMapping("/unlock-staff")
    public ResponseEntity<?> unlockStaff(@RequestParam String id){
        ResponseMessage message = staffService.unlockStaff(id);
        return new ResponseEntity<>(message, headers,OK);
    }
    @PostMapping("/change-role")
    public ResponseEntity<?> changeRole(@RequestBody @Valid ChangeRoleRequest request){
      ResponseMessage message = staffService.changeRole(request.id(), request.role());
       return new ResponseEntity<>(message, headers,OK);
    }
    @DeleteMapping("/delete-staff")
    public ResponseEntity<?> deleteStaff(@RequestParam String id){
        ResponseMessage message = staffService.deleteStaff(id);
        return new ResponseEntity<>(message, headers,OK);
    }
    @GetMapping("/get-staff")
    public ResponseEntity<?> getStaff(@RequestParam String  id){
        ResponseMessage message = staffService.getStaff(id);
        return new ResponseEntity<>(message, headers,OK);
    }
    @GetMapping("/get-all-staff")
    public ResponseEntity<?> getAllStaff(@RequestParam(value="page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        ResponseMessage message = staffService.getAllStaff(pageable);
     return new ResponseEntity<>(message, headers,OK);
    }
    @GetMapping("/get-roles")
    public ResponseEntity<?> getRoles(){
        return new ResponseEntity<>(staffService.getAllRoles(), headers, OK);
    }
}
