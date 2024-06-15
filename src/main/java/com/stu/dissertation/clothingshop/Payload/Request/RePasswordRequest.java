package com.stu.dissertation.clothingshop.Payload.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RePasswordRequest {
    private String email;
    private String newPassword;
    private String oldPassword;
    private String resetCode;
}
