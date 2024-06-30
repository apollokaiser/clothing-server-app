package com.stu.dissertation.clothingshop.Service.DonThue;

import com.stu.dissertation.clothingshop.DAO.DonThue.DonThueDAO;
import com.stu.dissertation.clothingshop.DAO.NguoiDung.NguoiDungDAO;
import com.stu.dissertation.clothingshop.DTO.ChiTietDonThueDTO;
import com.stu.dissertation.clothingshop.DTO.DonThueDTO;
import com.stu.dissertation.clothingshop.Entities.*;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.PaymentMethod;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.ChiTietDonThueMapper;
import com.stu.dissertation.clothingshop.Mapper.DonThueMapper;
import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.PhieuKhuyenMaiRepository;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import com.stu.dissertation.clothingshop.Repositories.TrangThaiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonThueServiceImpl implements DonThueService{
    private final DonThueDAO donThueDAO;
    private final NguoiDungDAO nguoiDungDAO;
    private final TrangThaiRepository trangThaiRepository;
    private final TrangPhucRepository trangPhucRepository;
    private final PhieuKhuyenMaiRepository phieuKhuyenMaiRepository;
    private final ChiTietDonThueMapper chiTietDonThueMapper;
    private final DonThueMapper donThueMapper;

    @Override
    @Transactional
    public ResponseMessage saveOrder(OrderRequest req) {
        DonThueDTO donThue = req.order();
        DonThue order = donThueMapper.convert(donThue);
        order.setMaDonThue(UUID.randomUUID().toString());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!email.isEmpty()) {
            NguoiDung nguoiDung = nguoiDungDAO.findNguoiDungByEmail(email)
                    .orElseThrow(()-> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND));
            if(!Objects.equals(nguoiDung.getId(), donThue.getNguoiDung()))
                throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
            order.setNguoiDung(nguoiDung);
            TrangThai trangThai = trangThaiRepository.findById(1L)
                    .orElseThrow(()-> new ApplicationException(BusinessErrorCode.INTERNAL_ERROR));
            order.setTrangThai(trangThai);
            if(!donThue.getPhieuKhuyenMai().isEmpty()) {
                PhieuKhuyenMai phieuKhuyenMai = phieuKhuyenMaiRepository.findById(donThue.getPhieuKhuyenMai())
                        .orElseThrow(()-> new ApplicationException(BusinessErrorCode.INVALID_PROMOTION_CODE));
                order.setPhieuKhuyenMai(phieuKhuyenMai);
            }
            List<TrangPhuc> listTrangPhuc = trangPhucRepository
                    .getTrangPhucByIds(donThue.getChiTiet().stream().map(ChiTietDonThueDTO::getMaTrangPhuc).toList());
            Set<ChiTietDonThue> chiTiet = donThue.getChiTiet().stream()
                    .map(detail -> {
                        ChiTietDonThue chiTietDonThue = chiTietDonThueMapper.convert(detail);
                        chiTietDonThue.setTrangPhuc(listTrangPhuc.stream()
                               .filter(trangPhuc -> trangPhuc.getId().equals(detail.getMaTrangPhuc()))
                               .findFirst()
                               .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NULL_DATA)));
                        chiTietDonThue.setMaChiTiet(UUID.randomUUID().toString());
                        chiTietDonThue.setDonThue(order);
                        return chiTietDonThue;
                    }).collect(Collectors.toSet());
            order.setChiTietDonThues(chiTiet);
            order.setPayment(PaymentMethod.valueOf(donThue.getPayment()));
            order.setNgayThue(Instant.now().getEpochSecond());
            donThueDAO.save(order);
        }
        return ResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Order has been created successfully")
                .build();
    }
}
