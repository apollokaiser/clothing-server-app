package com.stu.dissertation.clothingshop.Service.GioHang;

import com.stu.dissertation.clothingshop.DTO.GioHangDTO;
import com.stu.dissertation.clothingshop.Entities.Embedded.TrangPhuc_KichThuocKey;
import com.stu.dissertation.clothingshop.Entities.KichThuoc_TrangPhuc;
import com.stu.dissertation.clothingshop.Payload.Request.Cart;
import com.stu.dissertation.clothingshop.Repositories.KichThuocTrangPhucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class GioHangScheduler {
    @Autowired
    private KichThuocTrangPhucRepository kichThuocTrangPhucRepository;
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    @Async
    public void scheduleOutfitRestoration(Cart cart) {
        cancelScheduledRestoration(cart.identity());
        ScheduledFuture<?> future = Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            restoreOutfit(cart);
        }, 10, TimeUnit.MINUTES);
        scheduledTasks.put(cart.identity(), future);
    }
    @Async
    public void restoreOutfit(Cart cart) {
        List<TrangPhuc_KichThuocKey> ids = cart.carts().stream().map(item -> new TrangPhuc_KichThuocKey(item.getId(), item.getSize())).toList();
        List< KichThuoc_TrangPhuc > trangPhucs = kichThuocTrangPhucRepository.findAllById(ids);
        for (KichThuoc_TrangPhuc trangPhuc : trangPhucs) {
            TrangPhuc_KichThuocKey key = trangPhuc.getId();
            Optional<GioHangDTO> gioHang = cart.carts().stream()
                    .filter(item->
                            item.getId().equals(key.getMaTrangPhuc()) && item.getSize().equals(key.getMaKichThuoc()))
                    .findFirst();
            gioHang.ifPresent(gioHangDTO ->
                    trangPhuc.setSoLuong(trangPhuc.getSoLuong() + gioHangDTO.getQuantity()));
        }
        kichThuocTrangPhucRepository.saveAll(trangPhucs);
        scheduledTasks.remove(cart.identity());
    }
    private void cancelScheduledRestoration(String uid) {
        ScheduledFuture<?> future = scheduledTasks.get(uid);
        if (future != null) {
            future.cancel(false);
        }
    }
}
