package com.stu.dissertation.clothingshop.Service.DonThue;

import com.stu.dissertation.clothingshop.DTO.ChiTietDonThueDTO;
import com.stu.dissertation.clothingshop.DTO.DonThueDTO;
import com.stu.dissertation.clothingshop.DTO.DonThuePreviewDTO;
import com.stu.dissertation.clothingshop.DTO.PhieuHoanTraDTO;
import com.stu.dissertation.clothingshop.Entities.*;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.PaymentMethod;
import com.stu.dissertation.clothingshop.Enum.PaymentStatus;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.ChiTietDonThueMapper;
import com.stu.dissertation.clothingshop.Mapper.DonThueMapper;
import com.stu.dissertation.clothingshop.Mapper.PhieuHoanTraMapper;
import com.stu.dissertation.clothingshop.Payload.Request.DatCocRequest;
import com.stu.dissertation.clothingshop.Payload.Request.OrderRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.*;
import com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation.ManagerRequired;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class DonThueServiceImpl implements DonThueService{
    private final DonThueRepository donThueRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final TrangThaiRepository trangThaiRepository;
    private final TrangPhucRepository trangPhucRepository;
    private final PhieuKhuyenMaiRepository phieuKhuyenMaiRepository;
    private final PhieuHoanTraRepository phieuHoanTraRepository;
    private final DatCocRepository datCocRepository;
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
            NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
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
            donThueRepository.save(order);
        }
        return ResponseMessage.builder()
                .status(OK)
                .message("Order has been created successfully")
                .build();
    }
    @Transactional
    public ResponseMessage getOrder(String uid) {
        Set<DonThue> order = donThueRepository.getDonThueByUID(uid);
        List<DonThueDTO> donThueDTO = order.stream()
                .map(donThueMapper::convert).collect(Collectors.toList());
        return ResponseMessage.builder()
                .status(OK)
                .message("Order retrieved successfully")
                .data(new HashMap<>(){{
                    put("order", donThueDTO);
                }})
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage saveOrderWithoutAccount(OrderRequest req) {
        DonThueDTO donThue = req.order();
        DonThue order = donThueMapper.convert(donThue);
        order.setMaDonThue(UUID.randomUUID().toString());
            TrangThai trangThai = trangThaiRepository.findById(1L)
                    .orElseThrow(()-> new ApplicationException(BusinessErrorCode.INTERNAL_ERROR));
            order.setTrangThai(trangThai);
            if(!donThue.getPhieuKhuyenMai().isEmpty()) {
                PhieuKhuyenMai phieuKhuyenMai = phieuKhuyenMaiRepository.findById(donThue.getPhieuKhuyenMai())
                        .orElseThrow(()-> new ApplicationException(BusinessErrorCode.INVALID_PROMOTION_CODE));
                order.setPhieuKhuyenMai(phieuKhuyenMai);
            }
            List<TrangPhuc> listTrangPhuc = trangPhucRepository
                    .getTrangPhucByIds(donThue.getChiTiet()
                    .stream().map(ChiTietDonThueDTO::getMaTrangPhuc)
                    .toList());
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
            donThueRepository.save(order);
        return ResponseMessage.builder()
                .status(OK)
                .message("Order has been created successfully")
                .build();
    }

    @Override
    @Transactional
   @ManagerRequired
    public ResponseMessage getOrders(Pageable pageable, int status) {
        List<DonThue> orders = donThueRepository.getDonThue(status, pageable);
        List<DonThuePreviewDTO> donThueDTO = orders.stream().map(donThueMapper::convertPreview)
                .toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("Orders retrieved successfully")
                .data(new HashMap<>(){{
                    put("orders", donThueDTO);
                }})
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage changeStatus(String id, Long status) {
        TrangThai trangThai = trangThaiRepository.findById(status)
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        DonThue donThue = donThueRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        if(Objects.equals(trangThai.getTrangThai(), donThue.getTrangThai().getTrangThai())) {
            throw new ApplicationException(BusinessErrorCode.NO_CHANGE_APPLY);
        }
        if(trangThai.getMaTrangThai() < donThue.getTrangThai().getMaTrangThai()){
            throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
        }
        donThue.setTrangThai(trangThai);
        donThueRepository.save(donThue);
        return ResponseMessage.builder()
                .status(OK)
                .message("Change order status successfully")
                .data(new HashMap<>(){{
                    put("trangThai", trangThai);
                }})
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage addDeposit(DatCocRequest datCocRequest) {
        DonThue donThue = donThueRepository.findById(datCocRequest.maDonThue())
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        DatCoc datCoc = DatCoc.builder()
                .payment(PaymentMethod.valueOf(datCocRequest.payment()))
                .ngayCoc(Instant.now().getEpochSecond())
                .soTien(BigDecimal.valueOf(datCocRequest.soTien()))
                .donThue(donThue)
                .trangThai(PaymentStatus.valueOf(datCocRequest.trangThai()))
                .build();
        datCocRepository.save(datCoc);
        return ResponseMessage.builder()
                .status(OK)
                .message("Save deposit successfully")
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage printReturnBill(PhieuHoanTraDTO phieu) {
        DonThue donThue = donThueRepository.findById(phieu.getId())
                .orElseThrow(()->new ApplicationException(BusinessErrorCode.NOT_FOUND, "Cannot find order "));
        PhieuHoanTra phieuHoanTra = PhieuHoanTraMapper.INSTANCE.convert(phieu);
        phieuHoanTra.setDonThue(donThue);
        phieuHoanTra.setNgayTra(Instant.now().getEpochSecond());
        phieuHoanTraRepository.save(phieuHoanTra);
        return ResponseMessage.builder()
                .status(OK)
                .message("Save bill successfully")
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage unconfirmOrderCount() {
        int orderCount = donThueRepository.getOrdersCount(1 );
        return ResponseMessage.builder()
                .status(OK)
                .message("Unconfirm order count successfully")
                .data(new HashMap<>(){{
                    put("orderCount", orderCount);
                }})
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage getOrderDetail(String id) {
        DonThue order = donThueRepository.findById(id)
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        DonThueDTO donThueDTO = donThueMapper.convert(order);
        return ResponseMessage.builder()
                .status(OK)
                .message("Order detail retrieved successfully")
                .data(new HashMap<>(){{
                    put("order_detail", donThueDTO);
                }})
                .build();
    }
}
