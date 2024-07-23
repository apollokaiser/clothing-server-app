package com.stu.dissertation.clothingshop.Payload.Request;

import java.util.List;

public record DeleteOutfit(
        List<String> ids
) {
}
