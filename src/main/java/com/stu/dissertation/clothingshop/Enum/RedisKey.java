package com.stu.dissertation.clothingshop.Enum;

import lombok.Getter;

@Getter
public enum RedisKey {
    USER("user:"),
    ORDER("order:"),
    USER_CART("ucart:"),
    CART("cart:"),
    PRODUCT("product:"),
    SESIONS("session:"),
    CATEGORY("category:");
    private final String key;
    RedisKey(String key){
        this.key = key;
    }
}
