package com.stu.dissertation.clothingshop.Service.TrangPhuc;

import com.stu.dissertation.clothingshop.DAO.TrangPhuc.TrangPhucDAO;
import com.stu.dissertation.clothingshop.DTO.KichThuocTrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDetailDTO;
import com.stu.dissertation.clothingshop.DTO.UpdateTrangPhucDTO;
import com.stu.dissertation.clothingshop.Entities.*;
import com.stu.dissertation.clothingshop.Entities.Embedded.TrangPhuc_KichThuocKey;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.TrangPhucMapper;
import com.stu.dissertation.clothingshop.Payload.Request.CartID;
import com.stu.dissertation.clothingshop.Payload.Request.UpdateOutfit;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.KichThuocRepository;
import com.stu.dissertation.clothingshop.Repositories.TheLoaiRepository;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class TrangPhucServiceImpl implements TrangPhucService {
    private final TrangPhucDAO trangPhucDAO;
    private final TrangPhucMapper trangPhucMapper;
    private final KichThuocRepository kichThuocRepository;
    private final TrangPhucRepository trangPhucRepository;
    private final TheLoaiRepository theLoaiRepository;

    @Override
    @Transactional
    public ResponseMessage getInit(Pageable pageable, CartID attention) {
        ResponseMessage response = ResponseMessage.builder()
                .status(OK)
                .message("Get init data successfully")
                .build();
        Map<String, Object> datas = new HashMap<>();
        List<TrangPhuc> lastestOutfit = trangPhucRepository.getLastestOutfit(pageable);
        List<TrangPhucDTO> lastestDTOs = lastestOutfit.stream().map(trangPhucMapper::convert).toList();
        datas.put("lastest_outfit",lastestDTOs);
        if(attention!=null && !attention.ids().isEmpty()) {
            List<TrangPhuc> attentionOutfit = trangPhucRepository.findAllById(attention.ids());
            List<TrangPhucDTO> attentionDTOs = attentionOutfit.stream().map(trangPhucMapper::convert).toList();
            datas.put("attention_outfit", attentionDTOs);
        }
        response.setData(datas);
            return response;
    }

    @Override
    @Transactional
    public ResponseMessage getTrangPhuc(Pageable pageable) {
        List<TrangPhucDTO> trangPhucList = trangPhucRepository.findAll(pageable)
                .stream()
                .filter(trangphuc-> trangphuc.getTrangPhucChinhs().isEmpty())
                .map(trangPhucMapper::convert).toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("Get data successfully")
                .data(new HashMap<>() {{
                    put("trangphucs", trangPhucList);
                }})
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage getTrangPhucDetails(String id) {
        TrangPhucDetailDTO trangPhuc = trangPhucDAO.getTrangPhucDetails(id);
        return ResponseMessage.builder()
                .status(OK)
                .message("Get data successfully")
                .data(new HashMap<>() {{
                    put("trangPhuc", trangPhuc);
                }})
                .build();
    }

    @Override
    public ResponseMessage getTrangPhucInCart(List<String> ids) {
        List<TrangPhucDetailDTO> trangPhucs = trangPhucDAO.getTrangPhucInCart(ids);
        return ResponseMessage.builder()
                .status(OK)
                .message("Get cart detail data successfully")
                .data(new HashMap<>() {{
                    put("cart_details", trangPhucs);
                }})
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage searchTrangPhuc(String keyword, Pageable pageable) {
        String search = keyword.toLowerCase();
        List<TrangPhuc> trangPhucs = trangPhucRepository.searchOutfit(search, pageable);
        Set<TrangPhucDTO> trangPhucDTOS = trangPhucs.stream().map(trangPhucMapper::convert)
                .collect(Collectors.toSet());
        return ResponseMessage.builder()
                .status(OK)
                .message("Search successfully")
                .data(new HashMap<>() {{
                    put("trangphucs", trangPhucDTOS);
                }})
                .build();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') and " +
            "hasAnyAuthority('SUPER_ACCOUNT','FULL_CONTROL', 'OUTFIT_UPDATE')")
    public ResponseMessage addTrangPhuc(UpdateOutfit trangPhuc) {
        UpdateTrangPhucDTO dto = trangPhuc.trangPhuc();
        TrangPhuc outfit = trangPhucMapper.convert(dto);
        String id = generateOutfitID(dto.getTheLoai(), false);
        outfit.setId(id);
        TheLoai theLoai = theLoaiRepository.getReferenceById(dto.getTheLoai());
        outfit.setTheLoai(theLoai);
        outfit.setTinhTrang(true);
        if (!dto.getKichThuocs().isEmpty()) {
            List<String> kt = dto.getKichThuocs().stream()
                    .map(KichThuocTrangPhucDTO::getMaKichThuoc).toList();
            List<KichThuoc> kichThuocs = kichThuocRepository.getKichThuocByIds(kt);
            Set<KichThuoc_TrangPhuc> outfitSize = kichThuocs.stream().map(kichThuoc -> {
                KichThuocTrangPhucDTO dtoSize = dto.getKichThuocs().stream()
                        .filter(item -> Objects.equals(item.getMaKichThuoc(), kichThuoc.getId())).findFirst().get();
                return KichThuoc_TrangPhuc.builder()
                        .id(new TrangPhuc_KichThuocKey(kichThuoc.getId(), outfit.getId()))
                        .trangPhuc(outfit)
                        .kichThuoc(kichThuoc)
                        .soLuong(dtoSize.getSoLuong())
                        .build();
            }).collect(Collectors.toSet());
            outfit.setKichThuocTrangPhucs(outfitSize);
        }
        outfit.getHinhAnhs().forEach(item -> {
            item.setTrangPhuc(outfit);
        });
        trangPhucRepository.save(outfit);
        return ResponseMessage.builder()
                .status(OK)
                .message("Add outfit successfully")
                .build();
    }

    private String generateOutfitID(Long theLoai, boolean phoiSan) {
        long current = System.currentTimeMillis();
        int prefix = phoiSan ? 1 : 0;
        long currentTimeSeconds = Instant.now().getEpochSecond();
        return "TP" + prefix + theLoai + String.format("%05d", currentTimeSeconds % 100000);
    }
    @Override
    @Transactional
    public ResponseMessage updateOutfit(UpdateTrangPhucDTO dto) {
        TrangPhuc outfit = trangPhucRepository.findById(dto.getId())
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        trangPhucMapper.updateEntityFromDto(dto, outfit);
        if (!dto.getTheLoai().equals(outfit.getTheLoai().getMaLoai())) {
            TheLoai theLoai = theLoaiRepository.getReferenceById(dto.getTheLoai());
            outfit.setTheLoai(theLoai);
        }
        if (!dto.getHinhAnhs().isEmpty()) {
            Set<HinhAnhTrangPhuc> currentImages = outfit.getHinhAnhs();
            Set<HinhAnhTrangPhuc> newImages = new HashSet<>();

            dto.getHinhAnhs().forEach(img -> {
                HinhAnhTrangPhuc image = currentImages.stream()
                        .filter(ci -> ci.getHinhAnh().equals(img))
                        .findFirst()
                        .orElse(HinhAnhTrangPhuc.builder()
                                .hinhAnh(img)
                                .trangPhuc(outfit)
                                .build());
                newImages.add(image);
            });

            currentImages.clear();
            currentImages.addAll(newImages);
        }
        if (!dto.getKichThuocs().isEmpty()) {
            Set<KichThuoc_TrangPhuc> currentSizes = outfit.getKichThuocTrangPhucs();
            Set<KichThuoc_TrangPhuc> newSizes = new HashSet<>();

            List<String> ktIds = dto.getKichThuocs().stream()
                    .map(KichThuocTrangPhucDTO::getMaKichThuoc)
                    .toList();
            List<KichThuoc> kichThuocs = kichThuocRepository.getKichThuocByIds(ktIds);

            dto.getKichThuocs().forEach(dtoSize -> {
                KichThuoc kichThuoc = kichThuocs.stream()
                        .filter(kt -> kt.getId().equals(dtoSize.getMaKichThuoc()))
                        .findFirst()
                        .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));

                KichThuoc_TrangPhuc size = currentSizes.stream()
                        .filter(cs -> cs.getKichThuoc().equals(kichThuoc))
                        .findFirst()
                        .orElse(KichThuoc_TrangPhuc.builder()
                                .id(new TrangPhuc_KichThuocKey(kichThuoc.getId(), outfit.getId()))
                                .kichThuoc(kichThuoc)
                                .trangPhuc(outfit)
                                .build());

                size.setSoLuong(dtoSize.getSoLuong());
                newSizes.add(size);
            });

            currentSizes.clear();
            currentSizes.addAll(newSizes);
        }else  {
            outfit.getKichThuocTrangPhucs().clear();
        }
        trangPhucRepository.save(outfit);
        return ResponseMessage.builder()
                .status(OK)
                .message("Updated outfit successfully")
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage getAttentionOutfit(List<String> ids) {
     List<TrangPhuc> trangPhucs = trangPhucRepository.findAllById(ids);
    List<TrangPhucDTO> trangPhucDTOS = trangPhucs.stream().map(trangPhucMapper::convert).toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("Lock outfits successfully")
                .data(new HashMap<>(){{
                    put("attentions_outfit", trangPhucDTOS);
                }})
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage getLastestOutfit(Pageable pageable) {
    List<TrangPhuc> trangPhucs = trangPhucRepository.getLastestOutfit(pageable);
    List<TrangPhucDTO> trangPhucDTOS = trangPhucs.stream().map(trangPhucMapper::convert).toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("Lock outfits successfully")
                .data(new HashMap<>(){{
                    put("lastest_outfit", trangPhucDTOS);
                }})
                .build();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') and " +
            "hasAnyAuthority('SUPER_ACCOUNT','FULL_CONTROL')")
    public ResponseMessage lockTrangPhuc(List<String> ids) {
        List<TrangPhuc> trangPhuc = trangPhucRepository.findAllById(ids);
        trangPhuc.forEach(outfit -> outfit.setTinhTrang(false));
        trangPhucRepository.saveAll(trangPhuc);
        return ResponseMessage.builder()
                .status(OK)
                .message("Lock outfits successfully")
                .build();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') and " +
            "hasAnyAuthority('SUPER_ACCOUNT','FULL_CONTROL')")
    public ResponseMessage deleteTrangPhuc(List<String> ids) {
        trangPhucRepository.deleteAllById(ids);
        return ResponseMessage.builder()
                .status(OK)
                .message("Delete outfit successfully")
                .build();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') and " +
            "hasAnyAuthority('SUPER_ACCOUNT','FULL_CONTROL')")
    public ResponseMessage deleteTrangPhuc(String id) {
        trangPhucRepository.deleteById(id);
        return ResponseMessage.builder()
                .status(OK)
                .message("Delete outfit successfully")
                .build();
    }

    @Override
    @Transactional
    public ResponseMessage getDetailUpdate(String id) {
        TrangPhuc trangPhuc = trangPhucRepository.getReferenceById(id);
        UpdateTrangPhucDTO dto = trangPhucMapper.converToUpdateDTO(trangPhuc);
        return ResponseMessage.builder()
                .status(OK)
                .message("Get detail outfit successfully")
                .data(new HashMap<>() {{
                    put("trangPhuc", dto);
                }})
                .build();
    }
}
