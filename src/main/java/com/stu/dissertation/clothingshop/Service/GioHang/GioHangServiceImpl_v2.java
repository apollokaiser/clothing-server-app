package com.stu.dissertation.clothingshop.Service.GioHang;

import com.stu.dissertation.clothingshop.Cache.CacheService.GioHang.GioHangRedisService;
import com.stu.dissertation.clothingshop.DAO.TrangPhuc.TrangPhucDAO;
import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.DTO.OutfitCartDTO;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Security.JWTAuth.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Primary
public class GioHangServiceImpl_v2 implements GioHangService {
    private final TrangPhucDAO trangPhucDAO;
    private final GioHangRedisService gioHangRedisService;
    private final JWTService jwtService;

    private String getId() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String jwt = oauthToken.getToken().getTokenValue();
       return jwtService.extractUID(jwt);
    }
    @Override
    public ResponseMessage getCarts(String id) {
        List<GioHangDTO> cartItems = gioHangRedisService.getCart(id);
        if (cartItems == null) {
            throw new ApplicationException(BusinessErrorCode.NOT_FOUND);
        }
        List<String> ids = cartItems.stream().map(GioHangDTO::getParentId).toList();
        List<OutfitCartDTO> cartDetails = trangPhucDAO.getTrangPhucInCart(ids);
        return ResponseMessage.builder()
                .status(OK)
                .message("Get cart successfully")
                .data(new HashMap<>() {{
                    put("cart_items", cartItems);
                    put("cart_details", cartDetails);
                }})
                .build();
    }
    @Override
    public void updateCart( GioHangDTO cart) throws ParseException {
        gioHangRedisService.updateCart(getId(), cart);
    }
    @Override
    public void deleteCart(GioHangDTO cart) throws ParseException {
        gioHangRedisService.deleteCart(getId(), cart);
    }
    @Override
    public void clearCart(String id) {
        gioHangRedisService.clearCart(id);
    }
    @Override
    public void saveCart(GioHangDTO gioHangDTO) throws ParseException {
        gioHangRedisService.saveCart(getId(), gioHangDTO);
    }
    @Override
    public void deleteCart(List<GioHangDTO> gioHangDTOs) throws ParseException {
        gioHangRedisService.deleteCarts(getId(), gioHangDTOs);
    }
    @Override
    public void updateCart(GioHangDTO oldItem, GioHangDTO newItem) throws ParseException {
        gioHangRedisService.updateCart(getId(), oldItem, newItem);
    }
}
