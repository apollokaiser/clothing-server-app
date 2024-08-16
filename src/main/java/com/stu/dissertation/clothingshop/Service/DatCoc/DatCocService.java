package com.stu.dissertation.clothingshop.Service.DatCoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;

public interface DatCocService {
    ResponseMessage createVnPayPayment(HttpServletRequest request, OrderRequest order) throws JsonProcessingException, ParseException;
    void saveOrder(HttpServletRequest request);
}
