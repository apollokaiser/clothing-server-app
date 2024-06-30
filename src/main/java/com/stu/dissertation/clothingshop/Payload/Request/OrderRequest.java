package com.stu.dissertation.clothingshop.Payload.Request;
import com.stu.dissertation.clothingshop.DTO.DonThueDTO;
public record OrderRequest(
        DonThueDTO order
){}
