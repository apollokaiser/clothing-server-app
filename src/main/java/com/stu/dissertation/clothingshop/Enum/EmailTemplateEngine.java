package com.stu.dissertation.clothingshop.Enum;

import lombok.Getter;

@Getter
public enum EmailTemplateEngine {
    ACTIVATION_ACCOUNT("activation_account"),
    RESET_PASSWORD("reset_password"),
    SEND_ADMIN_ACCOUNT("send_admin_account"),
    ;
    private final String name;
    EmailTemplateEngine( String name){
        this.name = name;
    }
}
