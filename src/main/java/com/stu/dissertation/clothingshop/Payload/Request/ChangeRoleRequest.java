package com.stu.dissertation.clothingshop.Payload.Request;

import jakarta.validation.constraints.NotNull;

public record ChangeRoleRequest(
        @NotNull
        String id,
        @NotNull
        String role
) {
}
