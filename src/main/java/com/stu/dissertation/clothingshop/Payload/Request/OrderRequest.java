package com.stu.dissertation.clothingshop.Payload.Request;
import com.stu.dissertation.clothingshop.DTO.DonThueDTO;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Data
@Builder
@Jacksonized
public class OrderRequest{
        DonThueDTO order;
}
