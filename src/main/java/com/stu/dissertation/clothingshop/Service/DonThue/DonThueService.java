package com.stu.dissertation.clothingshop.Service.DonThue;

import com.stu.dissertation.clothingshop.DTO.PhieuHoanTraDTO;
import com.stu.dissertation.clothingshop.Entities.DatCoc;
import com.stu.dissertation.clothingshop.Payload.Request.DatCocRequest;
import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;

public interface DonThueService {
    ResponseMessage saveOrder(OrderRequest req);

    void saveOrder(OrderRequest req, String orderId, String uid, DatCoc datCoc);

    ResponseMessage getOrder(String uid);

    ResponseMessage saveOrderWithoutAccount(OrderRequest orderDetail, String sessionCode);

    ResponseMessage getOrders(Pageable pageable, int status);

    ResponseMessage changeStatus(String id, Long status);

    ResponseMessage addDeposit(DatCocRequest datCocRequest);

    ResponseMessage printReturnBill(PhieuHoanTraDTO phieuHoanTraDTO);

    ResponseMessage unconfirmOrderCount();

    ResponseMessage getOrderDetail(String id);

    ResponseMessage searchOrders(String keyword);

    ResponseMessage changeDate(Long date, String orderId);

    ResponseMessage updateTheChan(String id, long theChan);

    ResponseMessage updateNgayNhan(String id, long datetime);
}
