package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GioHangDTO implements Serializable {
    private String id;
    private int quantity;
    private String size;
    private String parentId;
    public GioHangDTO(
           @JsonProperty("id") String id,
           @JsonProperty("quantity") int quantity,
           @JsonProperty("size") String size,
           @JsonProperty("parentId") String parentId) {
        this.id = id;
        this.quantity = quantity;
        this.size = size;
        this.parentId = parentId;
    }

}
