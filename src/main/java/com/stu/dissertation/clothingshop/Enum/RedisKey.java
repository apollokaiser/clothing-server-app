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
    CATEGORY_PROMOTION("promotion:category:"),
    INVALID_TOKEN("invalid_token:"),
    USER_TOKEN("user_token:"),
    CATEGORY("category:"), PRE_ORDER("pre_order:");
    private final String key;
    RedisKey(String key){
        this.key = key;
    }
}
