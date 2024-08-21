package com.stu.dissertation.clothingshop.Controller.SocketIO;

import com.stu.dissertation.clothingshop.Payload.Response.PaymentStatusResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentController {
    private final SimpMessagingTemplate messagingTemplate;

    public PaymentController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/payment-status")
    public void sendPaymentStatus(PaymentStatusResponse message) {
        // Gửi thông báo đến client qua WebSocket
        messagingTemplate.convertAndSend("/topic/payment-status", message);
    }
}
