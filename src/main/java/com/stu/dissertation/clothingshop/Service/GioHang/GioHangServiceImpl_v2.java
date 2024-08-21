package com.stu.dissertation.clothingshop.Service.GioHang;

import com.stu.dissertation.clothingshop.Cache.CacheService.GioHang.GioHangRedisService;
import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.DTO.OutfitCartDTO;
import com.stu.dissertation.clothingshop.Entities.Embedded.TrangPhuc_KichThuocKey;
import com.stu.dissertation.clothingshop.Entities.KichThuoc_TrangPhuc;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.TrangphucDetailMapper;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.KichThuocTrangPhucRepository;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import com.stu.dissertation.clothingshop.Security.JWTAuth.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class GioHangServiceImpl_v2 implements GioHangService {
    private final TrangphucDetailMapper trangphucDetailMapper;
    private final TrangPhucRepository trangPhucRepository;
    private final GioHangRedisService gioHangRedisService;
    private final JWTService jwtService;
    private final KichThuocTrangPhucRepository kichThuocTrangPhucRepository;
    private final GioHangScheduler gioHangScheduler;
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
        //Lấy luôn cả prepare order
        Cart orders = gioHangRedisService.getPreOrder(id);
        List<String> ids = cartItems.stream().map(GioHangDTO::getParentId).toList();
        List<TrangPhuc> trangPhucs = trangPhucRepository.getTrangPhucByIds(ids);
        List<OutfitCartDTO> cartDetails = trangPhucs.stream()
                .map(trangphucDetailMapper::convertToCartItem)
                .toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("Get cart successfully")
                .data(new HashMap<>() {{
                    put("cart_items", cartItems);
                    put("cart_details", cartDetails);
                    put("prepare_cart", orders);
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
    @Override
    @Transactional
    public void prepareOrder(Cart cart) throws ParseException {
        log.info("preparing order: {}", new Date());
        gioHangScheduler.restoreOutfit(getUID());
        List<TrangPhuc_KichThuocKey> ids = cart.getCarts().stream().map(item -> new TrangPhuc_KichThuocKey(item.getId(), item.getSize())).toList();
        List< KichThuoc_TrangPhuc > trangPhucs = kichThuocTrangPhucRepository.findAllById(ids);
        for (KichThuoc_TrangPhuc trangPhuc : trangPhucs) {
            if(trangPhuc.getSoLuong() == 0 || !trangPhuc.getTrangThai()) {
                throw new ApplicationException(BusinessErrorCode.DATA_NOT_ENOUGH, "TrangPhuc is not enough to order");
            }
            TrangPhuc_KichThuocKey key = trangPhuc.getId();
            Optional<GioHangDTO> gioHang = cart.getCarts().stream()
                    .filter(item-> item.getId().equals(key.getMaTrangPhuc()) && item.getSize().equals(key.getMaKichThuoc()))
                    .findFirst();
            if (gioHang.isPresent()) {
                if (trangPhuc.getSoLuong() < gioHang.get().getQuantity()) {
                    throw new ApplicationException(BusinessErrorCode.DATA_NOT_ENOUGH, "TrangPhuc is not enough to order");
                }
                trangPhuc.setSoLuong(trangPhuc.getSoLuong() - gioHang.get().getQuantity());
            }
        }
        kichThuocTrangPhucRepository.saveAll(trangPhucs);
        gioHangScheduler.scheduleOutfitRestoration(cart);
    }
    @Override
   public void cancelPreparedOrder() throws ParseException {
        log.info("cancel order: {}", new Date());
    gioHangScheduler.restoreOutfit(getUID());
    }
    private String getUID () throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String jwt = oauthToken.getToken().getTokenValue();
        return jwtService.extractUID(jwt);
    }
}
