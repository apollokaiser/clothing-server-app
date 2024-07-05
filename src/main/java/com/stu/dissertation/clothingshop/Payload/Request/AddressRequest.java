package com.stu.dissertation.clothingshop.Payload.Request;

public record AddressRequest(
        String uid,
        Long id,
        String name,
        String address,
        Boolean isDefault,
        int provinceId,
        int districtId,
        int wardId
        ) {
}
