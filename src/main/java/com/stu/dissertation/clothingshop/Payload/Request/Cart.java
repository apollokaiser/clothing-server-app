package com.stu.dissertation.clothingshop.Payload.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Cart {
    @JsonProperty("identity")
    String identity;
    @JsonProperty("carts")
    List<GioHangDTO> carts;
    public Cart(String identity, List<GioHangDTO> carts) {
        this.identity = identity;
        this.carts = carts;
    }
    public Cart() {
    }
}
