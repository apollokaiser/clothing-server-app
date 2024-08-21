package com.stu.dissertation.clothingshop.Enum;

import lombok.Getter;

@Getter
public enum EmailTemplateEngine {
    ACTIVATION_ACCOUNT("activation_account"),
    RESET_PASSWORD("reset_password"),
    SEND_ADMIN_ACCOUNT("send_admin_account"),
    SEND_LOCK_ADMIN_ACCOUNT("send_lock_admin_account"),
    SEND_ORDER_INFO("send_order_info"),
    SEND_ENDING_ORDER("send_ending_order"),
    SEND_EXPIRED_REFUND("send_expires_refund"),
    ;
    private final String name;
    EmailTemplateEngine( String name){
        this.name = name;
    }
}
