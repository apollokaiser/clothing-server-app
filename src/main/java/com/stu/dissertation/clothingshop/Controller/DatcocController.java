package com.stu.dissertation.clothingshop.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import com.stu.dissertation.clothingshop.Payload.Response.PaymentStatusResponse;
import com.stu.dissertation.clothingshop.Service.DatCoc.DatCocService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class DatcocController {
    private final DatCocService datCocService;
    private final HttpHeaders headers;
    private final SimpMessagingTemplate messagingTemplate;
    @PostMapping("/dat-coc-don-thue")
    public ResponseEntity<?> deposit(HttpServletRequest request, @RequestBody @Valid OrderRequest order) throws JsonProcessingException, ParseException {
        return new ResponseEntity<>(datCocService.createVnPayPayment(request, order), headers, OK);
    }
    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler( HttpServletRequest request,
                                                HttpServletResponse response,
                                                @RequestParam(value = "vnp_ResponseCode") String statusCode) throws IOException {
            String url = "http://localhost:5173/thanh-toan/trang-thai-thanh-toan/" + request.getParameter("vnp_TxnRef") + "?status=" ;
        if (statusCode.equals("00")) {
            datCocService.saveOrder(request);
            PaymentStatusResponse result = new PaymentStatusResponse(200, "OK");
            url = url+ "success";
            messagingTemplate.convertAndSend("/topic/payment-status", result);
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect(url);
            //what we can do here ????
        } else {
            url = url+ "failed";
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect(url);
        }
    }

}
