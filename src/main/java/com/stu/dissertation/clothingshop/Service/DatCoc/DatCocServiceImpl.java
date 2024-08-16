package com.stu.dissertation.clothingshop.Service.DatCoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stu.dissertation.clothingshop.Cache.CacheService.DonThue.DonThueRedisService;
import com.stu.dissertation.clothingshop.Config.VNPAYConfig;
import com.stu.dissertation.clothingshop.Entities.DatCoc;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.PaymentMethod;
import com.stu.dissertation.clothingshop.Enum.PaymentStatus;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Security.JWTAuth.JWTService;
import com.stu.dissertation.clothingshop.Service.DonThue.DonThueService;
import com.stu.dissertation.clothingshop.Utils.RandomCodeGenerator;
import com.stu.dissertation.clothingshop.Utils.VnPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class DatCocServiceImpl implements DatCocService {
    private final VNPAYConfig vnPayConfig;
    private final DonThueRedisService donThueRedisService;
    private final JWTService jwtService;
    private final DonThueService donThueService;

    @Override
    public ResponseMessage createVnPayPayment(HttpServletRequest request, OrderRequest order) throws JsonProcessingException, ParseException {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        String orderId = RandomCodeGenerator.generateOrderCode();
        String orderInfo = String.format("Dat coc don thue: %s, so tien thanh toan: %d,  khach hang: %s", orderId,amount, getId());
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_OrderInfo", orderInfo);
        vnpParamsMap.put("vnp_TxnRef", orderId);
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VnPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VnPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VnPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VnPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        String uid = getId();
        if(donThueRedisService.getDonThue(orderId,uid, false) != null) {
            throw new ApplicationException(BusinessErrorCode.PROCESSING_ERROR, "Payment has been processed by other user");
        }
        donThueRedisService.saveDonThue(order, orderId, uid);
        return ResponseMessage.builder()
                .status(OK)
                .message("get payment url successfully")
                .data(new HashMap<>() {{
                    put("paymentUrl", paymentUrl);
                    put("order_id", orderId);
                }})
                .build();
    }


    private String getId() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String jwt = oauthToken.getToken().getTokenValue();
        return jwtService.extractUID(jwt);
    }
    @Override
    public void saveOrder(HttpServletRequest request) {
        String amount = request.getParameter("vnp_Amount");
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String[] data = orderInfo.split(",");
        String uid = data[2].split(":")[1].trim();
        String orderId = request.getParameter("vnp_TxnRef");
        OrderRequest orderRequest = donThueRedisService.getDonThue(orderId, uid, true);
        Long giatien = Long.parseLong(amount);
        giatien = giatien /100L;
        DatCoc datCoc = DatCoc.builder()
                .trangThai(PaymentStatus.SUCCESS)
                .soTien(BigDecimal.valueOf(giatien))
                .noiDungDatCoc(orderInfo)
                .payment(PaymentMethod.ONLINE)
                .ngayCoc(Instant.now().getEpochSecond())
                .build();
        donThueService.saveOrder(orderRequest,orderId,uid,datCoc);
    }
}
