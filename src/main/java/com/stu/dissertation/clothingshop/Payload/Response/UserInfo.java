package com.stu.dissertation.clothingshop.Payload.Response;

public record UserInfo (
        String id,
        String name,
        String given_name,
        String family_name,
        String picture,
        String email,
        Boolean verified_email,
        String locale
) {
}
