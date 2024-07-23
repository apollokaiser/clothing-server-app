package com.stu.dissertation.clothingshop.Payload.Request;

import com.stu.dissertation.clothingshop.DTO.DiaChiDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AdminInfo(
        @NotEmpty
        String name,
        @NotEmpty
        @Email
        String email,
        @NotEmpty
        String phone,
        @NotEmpty
        String role,
        DiaChiDTO diaChi
) {
}
