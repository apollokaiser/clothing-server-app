package com.stu.dissertation.clothingshop.Enum;

import lombok.Getter;

@Getter
public enum ROLE {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    OUTFIT_STAFF("OUTFIT_UPDATE"),
    PROMOTION_STAFF("PROMOTION_UPDATE"),
    MANAGER("FULL_CONTROL"),
    HIGHEST_ROLE("SUPER_ACCOUNT");
    private final String role;
    ROLE(String role){
        this.role = role;
    }
}
