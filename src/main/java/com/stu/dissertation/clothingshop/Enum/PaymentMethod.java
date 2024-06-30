package com.stu.dissertation.clothingshop.Enum;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    ONLINE("online"),
    DIRECT("direct");
    private final String value;
    PaymentMethod(String value){
        this.value = value;
    }
}
