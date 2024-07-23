package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminStaffDetailDTO {
    private String id;
    private String tenNguoiDung;
    private String sdt;
    private String email;
    private boolean enabled;
    private String adminEmail;
    private Long lastLogin;
    private String role;
    private int soDonThue;
    private String createdBy;
    private Long createAt;
}
