package com.stu.dissertation.clothingshop.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RePasswordRequest {
    private String email;
    @NotBlank
    private String newPassword;
    private String oldPassword;
    private String resetCode;
}
