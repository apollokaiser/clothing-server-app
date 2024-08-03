package com.stu.dissertation.clothingshop.Service.KhuyenMai;

import com.stu.dissertation.clothingshop.Cache.CacheService.KhuyenMai.KhuyenMaiRedisService;
import com.stu.dissertation.clothingshop.DTO.KhuyenMaiDTO;
import com.stu.dissertation.clothingshop.DTO.KhuyenMaiThanhToanDTO;
import com.stu.dissertation.clothingshop.DTO.KhuyenMaiTheLoaiDTO;
import com.stu.dissertation.clothingshop.Entities.KhuyenMai;
import com.stu.dissertation.clothingshop.Entities.TheLoai;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.KhuyenMaiMapper;
import com.stu.dissertation.clothingshop.Payload.Request.AddpromotionRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.KhuyenMaiRepository;
import com.stu.dissertation.clothingshop.Repositories.TheLoaiRepository;
import com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation.AdminRequired;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class KhuyenMaiServiceImpl implements KhuyenMaiService{
    private final KhuyenMaiMapper khuyenMaiMapper;
    private final KhuyenMaiRepository khuyenMaiRepository;
    private final TheLoaiRepository theLoaiRepository;
    private final KhuyenMaiRedisService khuyenMaiRedisService;
    @Override
    @Transactional
    public ResponseMessage getKhuyenMaiThanhToan() {
        Set<KhuyenMai> khuyenMaiThanhToan = khuyenMaiRepository.getUnGroupPromotions();
        List<KhuyenMaiThanhToanDTO> khuyenMais = khuyenMaiThanhToan.stream()
                .map(khuyenMaiMapper::convertToPromotionPayment).collect(Collectors.toList());
        return ResponseMessage.builder()
                .status(OK)
                .message("Get khuyen mai thannh toan successfully")
                .data(new HashMap<>(){{
                    put("khuyenmai_thanhtoans", khuyenMais);
                }})
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') and " +
                "hasAnyAuthority('SUPER_ACCOUNT','FULL_CONTROL', 'PROMOTION_UPDATE')")
    @Transactional
    public ResponseMessage savePromotion(AddpromotionRequest promotion) {
        KhuyenMai khuyenMai = khuyenMaiMapper.convert(promotion.khuyenMai());
        boolean isPresent = khuyenMaiRepository.findKhuyenMaiByTenKhuyenMai(khuyenMai.getTenKhuyenMai()).isPresent();
        if(isPresent) throw new ApplicationException(BusinessErrorCode.DATA_EXISTS);
        KhuyenMai thisPromotion = khuyenMaiRepository.save(khuyenMai);
        Long thisTime = thisPromotion.getNgayBatDau();
        promotion.ids().forEach(id-> khuyenMaiRepository.addPromotion(thisPromotion.getMaKhuyenMai(), id, thisTime));
        return ResponseMessage.builder()
                .status(OK)
                .message("Save promotion successfully")
                .data(new HashMap<>(){{
                    put("promotion_id", thisPromotion.getMaKhuyenMai());

                }})
                .build();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') and hasAnyAuthority('SUPER_ACCOUNT','FULL_CONTROL')")
    public ResponseMessage deletePromotion(Long id) {
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        List<Long> ids = khuyenMai.getTheLoais().stream().map(TheLoai::getMaLoai).toList();
        List<TheLoai> theLoais = theLoaiRepository.findAllById(ids);
        khuyenMaiRepository.delete(khuyenMai);
        theLoais.forEach(theLoai -> theLoai.getKhuyenMais().remove(khuyenMai));
            theLoaiRepository.saveAllAndFlush(theLoais);
        return ResponseMessage.builder()
                .status(OK)
                .message("Delete promotion successfully")
                .build();
    }

    @Override
    public ResponseMessage getPromotionList() {
        long currentTime = Instant.now().getEpochSecond();
        List<KhuyenMai> khuyenMais  = khuyenMaiRepository.getPromotionsNonExpired(currentTime);
        List<KhuyenMaiDTO> khuyenMaisDTOs = khuyenMais.stream()
                .map(khuyenMaiMapper::convert).toList();
     return ResponseMessage.builder()
             .status(OK)
             .message("Get promotion list successfully")
             .data( new HashMap<>(){{
                 put("khuyenmais", khuyenMaisDTOs);
             }})
             .build();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') and hasAnyAuthority('SUPER_ACCOUNT','FULL_CONTROL')")
    public ResponseMessage updatePromotion(AddpromotionRequest promotion) {
        KhuyenMai khuyenMai = khuyenMaiMapper.convert(promotion.khuyenMai());
        if(khuyenMai.getMaKhuyenMai()==null) {
            throw new ApplicationException(BusinessErrorCode.NOT_FOUND);
        }
        KhuyenMai entity = khuyenMaiRepository.findById(khuyenMai.getMaKhuyenMai())
                .orElseThrow(()->new ApplicationException(BusinessErrorCode.NOT_FOUND));
        List<Long> ids = entity.getTheLoais().stream().map(TheLoai::getMaLoai).toList();
        List<TheLoai> theLoais = theLoaiRepository.findAllById(ids);
        if(!promotion.ids().isEmpty()) {
            theLoais.forEach((theLoai ->{
                entity.getTheLoais().remove(theLoai);
                theLoai.getKhuyenMais().remove(entity);
            }));
            updateField(entity, khuyenMai);
           KhuyenMai savedPromotion =  khuyenMaiRepository.save(entity);
            theLoaiRepository.saveAll(theLoais);
            promotion.ids().forEach(id -> khuyenMaiRepository.addPromotion(savedPromotion.getMaKhuyenMai(), id, savedPromotion.getNgayBatDau()));
        }
        updateField(entity, khuyenMai);
       khuyenMaiRepository.save(entity);
       return ResponseMessage.builder()
               .status(OK)
               .message("update successfully")
               .build();
    }

    @Override
    public ResponseMessage getCategoryInPromotion(Long id) {
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        List<Long> categoryId = khuyenMai.getTheLoais()
                .stream().map(TheLoai::getMaLoai).toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("Get category in promotion successfully")
                .data( new HashMap<>(){{
                     put("category_ids", categoryId);
                 }})
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage getPromotionsCategory() {
      ResponseMessage response =  ResponseMessage.builder()
                .status(OK)
                .message("get promotion categories successfully")
                .build();
        List<KhuyenMaiTheLoaiDTO> promotions = khuyenMaiRedisService.getCategoryPromotions();
        if(promotions == null) {
            long currentTime = Instant.now().getEpochSecond();
            List<KhuyenMai> khuyenMais = khuyenMaiRepository
                    .getPromotionsCategory(currentTime);
            List<KhuyenMaiTheLoaiDTO> khuyenMaisDTOs = khuyenMais.stream()
                    .map(khuyenMaiMapper::convertPromtionCategory).toList();
            khuyenMaiRedisService.addCategoryPromotions(khuyenMaisDTOs);
            response.setData(new HashMap<>(){{
                put("theloai_promotions", khuyenMaisDTOs);
            }});
        }else {
            response.setData(new HashMap<>(){{
                put("theloai_promotions", promotions);
            }});
        }
        return response;
    }

    @Override
    @Transactional
    @AdminRequired
    public ResponseMessage getAllPromotion(Pageable pageable) {
        Page<KhuyenMai> khuyenMais = khuyenMaiRepository.findAll(pageable);
        List<KhuyenMai> promotions = khuyenMais.stream().toList();
        List<KhuyenMaiDTO> khuyenMaiDTOs = promotions.stream().map(khuyenMaiMapper::convert).toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("get promotion categories successfully")
                .data(new HashMap<>(){{
                    put("promotions",khuyenMaiDTOs);
                }})
                .build();
    }

    private void updateField(KhuyenMai khuyenMai, KhuyenMai update) {
        if(update.getTenKhuyenMai()!= null) khuyenMai.setTenKhuyenMai(update.getTenKhuyenMai());
        if(update.getMoTa()!= null) khuyenMai.setMoTa(update.getMoTa());
        if(update.getNgayBatDau()!= null) khuyenMai.setNgayBatDau(update.getNgayBatDau());
        if(update.getNgayKetThuc()!= null) khuyenMai.setNgayKetThuc(update.getNgayKetThuc());
        if(update.getGiamTien()!= null) khuyenMai.setGiamTien(update.getGiamTien());
        if(update.getGiamToiDa()!=null) khuyenMai.setGiamToiDa(update.getGiamToiDa());
        if(update.getPhanTramGiam() !=null) khuyenMai.setPhanTramGiam(update.getPhanTramGiam());
        if(update.getGiaTriToiThieu() !=null) khuyenMai.setGiaTriToiThieu(update.getGiaTriToiThieu());
        if(update.getSoLuongToiThieu() !=null) khuyenMai.setSoLuongToiThieu(update.getSoLuongToiThieu());
    }
}
