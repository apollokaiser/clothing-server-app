package com.stu.dissertation.clothingshop.Service.DonThue;

import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;

public interface DonThueService {
    ResponseMessage saveOrder(OrderRequest order);
}
