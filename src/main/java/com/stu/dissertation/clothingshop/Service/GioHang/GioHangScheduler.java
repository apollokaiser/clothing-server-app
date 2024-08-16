package com.stu.dissertation.clothingshop.Service.GioHang;

import com.stu.dissertation.clothingshop.Cache.CacheService.GioHang.GioHangRedisService;
import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Entities.Embedded.TrangPhuc_KichThuocKey;
import com.stu.dissertation.clothingshop.Entities.KichThuoc_TrangPhuc;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;
import com.stu.dissertation.clothingshop.Repositories.KichThuocTrangPhucRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

@Slf4j
@Service
public class GioHangScheduler {
    @Autowired
    private KichThuocTrangPhucRepository kichThuocTrangPhucRepository;
    @Autowired
    private ScheduledExecutorService scheduledExecutorService;
    @Autowired
    private GioHangRedisService gioHangRedisService;
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    public void scheduleOutfitRestoration(Cart cart) {
        gioHangRedisService.savePreOrder(cart.getIdentity(), cart);
        cancelScheduledRestoration(cart.getIdentity());
        ScheduledFuture<?> future = scheduledExecutorService.schedule(() -> {
            restoreOutfit(cart);
        }, 10, TimeUnit.MINUTES);
        scheduledTasks.put(cart.getIdentity(), future);
    }
    @Transactional
    public void restoreOutfit(Cart cart) {
        try {
            List<TrangPhuc_KichThuocKey> ids = cart.getCarts().stream().map(item -> new TrangPhuc_KichThuocKey(item.getId(), item.getSize())).toList();
            List< KichThuoc_TrangPhuc > trangPhucs = kichThuocTrangPhucRepository.findAllById(ids);
            for (KichThuoc_TrangPhuc trangPhuc : trangPhucs) {
                TrangPhuc_KichThuocKey key = trangPhuc.getId();
                Optional<GioHangDTO> gioHang = cart.getCarts().stream()
                        .filter(item->
                                item.getId().equals(key.getMaTrangPhuc()) && item.getSize().equals(key.getMaKichThuoc()))
                        .findFirst();
                gioHang.ifPresent(gioHangDTO ->
                        trangPhuc.setSoLuong(trangPhuc.getSoLuong() + gioHangDTO.getQuantity()));
            }
            kichThuocTrangPhucRepository.saveAll(trangPhucs);
            scheduledTasks.remove(cart.getIdentity());
        }catch (Exception e) {
            System.err.println("Error restoring outfit: " + e.getMessage());
        }
    }
    @Transactional
    public void restoreOutfit(String id) {
        Cart cart = gioHangRedisService.getPreOrder(id);
        if (cart == null) return;
        try {
            List<TrangPhuc_KichThuocKey> ids = cart.getCarts().stream().map(item -> new TrangPhuc_KichThuocKey(item.getId(), item.getSize())).toList();
            List< KichThuoc_TrangPhuc > trangPhucs = kichThuocTrangPhucRepository.findAllById(ids);
            for (KichThuoc_TrangPhuc trangPhuc : trangPhucs) {
                TrangPhuc_KichThuocKey key = trangPhuc.getId();
                Optional<GioHangDTO> gioHang = cart.getCarts().stream()
                        .filter(item->
                                item.getId().equals(key.getMaTrangPhuc()) && item.getSize().equals(key.getMaKichThuoc()))
                        .findFirst();
                gioHang.ifPresent(gioHangDTO ->
                        trangPhuc.setSoLuong(trangPhuc.getSoLuong() + gioHangDTO.getQuantity()));
            }
            scheduledTasks.remove(cart.getIdentity());
            gioHangRedisService.clearPreOrder(id);
            kichThuocTrangPhucRepository.saveAll(trangPhucs);
        }catch (Exception e) {
            System.err.println("Error restoring outfit: " + e.getMessage());
        }
    }
    public void cancelScheduledRestoration(String uid) {
        log.info("Cancelling scheduled");
        ScheduledFuture<?> future = scheduledTasks.get(uid);
        if (future != null) {
            future.cancel(false);
        }
    }
}
