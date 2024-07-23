package com.stu.dissertation.clothingshop.DTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminStaffDTO {
    private String id;
    private String tenNguoiDung;
    private String sdt;
    private boolean enabled;
    private Long lastLogin;
    private String role;
}
