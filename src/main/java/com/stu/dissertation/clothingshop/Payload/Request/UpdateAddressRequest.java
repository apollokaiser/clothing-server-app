package com.stu.dissertation.clothingshop.Payload.Request;

import java.util.List;

public record UpdateAddressRequest(
    AddressRequest address,
    boolean updateDefault
) {
}
