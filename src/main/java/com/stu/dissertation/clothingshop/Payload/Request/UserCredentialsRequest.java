package com.stu.dissertation.clothingshop.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsRequest {
    private String email;
    @NotBlank(message = "Please enter your email address")
    @NotEmpty
    @NotNull
    private String password;
    private String name = null;
}
