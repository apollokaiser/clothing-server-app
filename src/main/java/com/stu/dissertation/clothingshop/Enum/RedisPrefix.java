package com.stu.dissertation.clothingshop.Enum;

import lombok.Getter;

@Getter
public enum RedisPrefix {
    CART_PREFIX("outfit_");
    private final String prefix;
    RedisPrefix(String prefix) {
        this.prefix = prefix;
    }

}
