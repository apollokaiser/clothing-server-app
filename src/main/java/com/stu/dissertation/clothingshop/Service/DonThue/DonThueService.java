package com.stu.dissertation.clothingshop.Service.DonThue;

import com.stu.dissertation.clothingshop.DTO.PhieuHoanTraDTO;
import com.stu.dissertation.clothingshop.Payload.Request.DatCocRequest;
import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import org.springframework.data.domain.Pageable;

public interface DonThueService {
    ResponseMessage saveOrder(OrderRequest order);
    ResponseMessage getOrder(String uid);
    ResponseMessage saveOrderWithoutAccount(OrderRequest orderDetail, String sessionCode);

    ResponseMessage getOrders(Pageable pageable, int status);

    ResponseMessage changeStatus(String id, Long status);

    ResponseMessage addDeposit(DatCocRequest datCocRequest);

    ResponseMessage printReturnBill(PhieuHoanTraDTO phieuHoanTraDTO);

    ResponseMessage unconfirmOrderCount();

    ResponseMessage getOrderDetail(String id);
}
