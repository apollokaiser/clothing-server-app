package com.stu.dissertation.clothingshop.Enum;

import lombok.Getter;

@Getter
public enum EmailTemplateEngine {
    ACTIVATION_ACCOUNT("activation_account"),
    RESET_PASSWORD("reset_password");
    private final String name;
    EmailTemplateEngine( String name){
        this.name = name;
    }
}
