package com.stu.dissertation.clothingshop.Service.TrangPhuc;

import com.stu.dissertation.clothingshop.DTO.UpdateTrangPhucDTO;
import com.stu.dissertation.clothingshop.Payload.Request.CartID;
import com.stu.dissertation.clothingshop.Payload.Request.UpdateOutfit;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrangPhucService {
    ResponseMessage getInit(Pageable pageable, CartID attention);
    ResponseMessage getTrangPhuc(Pageable pageable);
    ResponseMessage getTrangPhucDetails(String id);
    ResponseMessage getTrangPhucInCart(List<String> ids);
    ResponseMessage searchTrangPhuc(String search, Pageable pageable);
    ResponseMessage addTrangPhuc(UpdateOutfit trangPhuc);
    ResponseMessage lockTrangPhuc(List<String> ids);
    ResponseMessage deleteTrangPhuc(List<String> ids);
    ResponseMessage deleteTrangPhuc(String id);
    ResponseMessage getDetailUpdate(String id);
    ResponseMessage updateOutfit (UpdateTrangPhucDTO dto);
    ResponseMessage getAttentionOutfit(List<String> ids);
    ResponseMessage getLastestOutfit(Pageable pageable);
}
