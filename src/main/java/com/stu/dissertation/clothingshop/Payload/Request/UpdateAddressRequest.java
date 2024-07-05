package com.stu.dissertation.clothingshop.Payload.Request;

import java.util.List;

public record UpdateAddressRequest(
        List<AddressRequest> updateAddress,
        List<AddressRequest> deleteAddress,
        String email
) {
}
