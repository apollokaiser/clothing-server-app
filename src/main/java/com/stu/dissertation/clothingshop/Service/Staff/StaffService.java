package com.stu.dissertation.clothingshop.Service.Staff;

import com.stu.dissertation.clothingshop.Payload.Request.AdminInfo;
import com.stu.dissertation.clothingshop.Payload.Request.UpdateAdminInfo;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Pageable;

public interface StaffService {
    ResponseMessage addStaff (AdminInfo info) throws MessagingException;
    ResponseMessage deleteStaff (String id);
    ResponseMessage updateStaff ( UpdateAdminInfo info);
    ResponseMessage lockStaff (String id);
    ResponseMessage unlockStaff (String id);
    ResponseMessage getAllStaff(Pageable pageable);
    ResponseMessage getStaff (String id);
    ResponseMessage changeRole(String id, String role);
    ResponseMessage getAllRoles();
}
