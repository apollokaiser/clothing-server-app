package com.stu.dissertation.clothingshop.Payload.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UpdateUserRequest {
    private String email;
    private String name;
    private String phone;
}
