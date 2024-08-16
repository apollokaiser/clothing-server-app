package com.stu.dissertation.clothingshop.Service.TrangPhuc;

import com.stu.dissertation.clothingshop.DAO.TrangPhuc.TrangPhucDAO;
import com.stu.dissertation.clothingshop.DTO.*;
import com.stu.dissertation.clothingshop.Entities.*;
import com.stu.dissertation.clothingshop.Entities.Embedded.TrangPhuc_KichThuocKey;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.SIZE;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.TrangPhucMapper;
import com.stu.dissertation.clothingshop.Payload.Request.CartID;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.KichThuocRepository;
import com.stu.dissertation.clothingshop.Repositories.KichThuocTrangPhucRepository;
import com.stu.dissertation.clothingshop.Repositories.TheLoaiRepository;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation.ManagerRequired;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
    private final KichThuocTrangPhucRepository kichThuocTrangPhucRepository;

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
        datas.put("lastest_outfit", lastestDTOs);
        if (attention != null && !attention.ids().isEmpty()) {
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
                .filter(trangphuc -> trangphuc.getTrangPhucChinh() == null)
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
    @Transactional
    public ResponseMessage getTrangPhucInCart(List<String> ids) {
        List<OutfitCartDTO> trangPhucs = trangPhucDAO.getTrangPhucInCart(ids);
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
    public ResponseMessage addTrangPhuc(UpdateTrangPhucDTO dto) {
        TrangPhuc outfit = trangPhucMapper.convert(dto);
        String id = generateOutfitID(dto.getTheLoai(), outfit.isHasPiece());
        outfit.setId(id);
        TheLoai theLoai = theLoaiRepository.findById(dto.getTheLoai())
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        outfit.setTheLoai(theLoai);
        outfit.setTinhTrang(true);
        List<KichThuoc> kichThuocEntity = kichThuocRepository.findAll();
        // tạm thời phong ấn coi có lỗi gì không ! Có thì lấy lại
//        List<String> size = dto.getKichThuocs().stream()
//                .map(KichThuocTrangPhucDTO::getMaKichThuoc).toList();
//        // tạo 1 set KichThuoc_TrangPhuc cho outfit cần thêm
//        Set<KichThuoc_TrangPhuc> outfitSize = kichThuocEntity.stream().map(kichThuoc -> {
//            KichThuocTrangPhucDTO dtoSize = dto.getKichThuocs().stream()
//                    .filter(item -> Objects.equals(item.getMaKichThuoc(), kichThuoc.getId())).findFirst().get();
//            return KichThuoc_TrangPhuc.builder()
//                    .id(new TrangPhuc_KichThuocKey(kichThuoc.getId(), outfit.getId()))
//                    .trangPhuc(outfit)
//                    .kichThuoc(kichThuoc)
//                    .soLuong(dtoSize.getSoLuong())
//                    .build();
//        }).collect(Collectors.toSet());
//        outfit.setKichThuocTrangPhucs(outfitSize);
        // xem hàm setOutfitSize() bên dưới
        outfit.setKichThuocTrangPhucs(setOutfitSize(dto, outfit, kichThuocEntity));
        if (dto.isHasPiece() && dto.getManhTrangPhucs().isEmpty()) {
            throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
        }
        AtomicInteger index = new AtomicInteger(0);
        outfit.getManhTrangPhucs().forEach(item -> {
            UpdateTrangPhucDTO mangTrangPhucDTO = dto.getManhTrangPhucs().get(index.get());
            item.setId(id + index);
            item.setTinhTrang(true);
            item.setTheLoai(theLoai);
            item.setTrangPhucChinh(outfit);
            item.setKichThuocTrangPhucs(setOutfitSize(mangTrangPhucDTO, item, kichThuocEntity));
            index.getAndIncrement();
        });
        for (HinhAnhTrangPhuc item : outfit.getHinhAnhs()) item.setTrangPhuc(outfit);
        trangPhucRepository.save(outfit);
        return ResponseMessage.builder()
                .status(OK)
                .message("Add outfit successfully")
                .build();
    }

    @Transactional
    private Set<KichThuoc_TrangPhuc> setOutfitSize(
            UpdateTrangPhucDTO dto,
            TrangPhuc outfit,
            @NonNull List<KichThuoc> kichThuocEntity) {
        if (outfit.getTheLoai().getForAccessary() != null && outfit.getTheLoai().getForAccessary()) {
            if (dto.getKichThuocs().size() != 1)
                throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
        }
        return dto.getKichThuocs().stream().map(dtoSize -> {
            Optional<KichThuoc> kichThuoc = kichThuocEntity.stream()
                    .filter(item -> Objects.equals(item.getId(), dtoSize.getMaKichThuoc())).findFirst();
            if (kichThuoc.isEmpty()) throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
            return KichThuoc_TrangPhuc.builder()
                    .id(new TrangPhuc_KichThuocKey(outfit.getId(), dtoSize.getMaKichThuoc()))
                    .trangPhuc(outfit)
                    .kichThuoc(kichThuoc.get())
                    .tonKho(dtoSize.getTonKho())
                    .soLuong(dtoSize.getTonKho())
                    .trangThai(true)
                    .build();
        }).collect(Collectors.toSet());
    }

    @Transactional
    private void updateOutfitSize(
            UpdateTrangPhucDTO dto,
            TrangPhuc outfit,
            @NonNull List<KichThuoc> kichThuocEntity) {
        if (outfit.getTheLoai().getForAccessary() != null && outfit.getTheLoai().getForAccessary()) {
            if (dto.getKichThuocs().size() != 1)
                throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
        }
        dto.getKichThuocs().forEach(dtoSize -> {
            Optional<KichThuoc> kichThuoc = kichThuocEntity.stream()
                    .filter(item -> Objects.equals(item.getId(), dtoSize.getMaKichThuoc())).findFirst();
            if (kichThuoc.isEmpty()) throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
            boolean isNew = true;
            for (KichThuoc_TrangPhuc size : outfit.getKichThuocTrangPhucs()) {
                if (size.getKichThuoc().getId().equals(kichThuoc.get().getId())) {
                    //chỉ cho phép thay đổi khi sản phẩm đã thu hồi hết
                    if (dtoSize.getTonKho() < size.getTonKho()) {
                        //nếu chưa thu hồi hết
                        if (!Objects.equals(size.getSoLuong(), size.getTonKho())) {
                            throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
                        } else {
                            size.setTonKho(dtoSize.getTonKho());
                            size.setSoLuong(dtoSize.getTonKho());
                        }
                    } else {
                        // số lượng cho phép thuê sẽ = số lượng mới - số lượng cũ + số còn lại
                        size.setSoLuong(dtoSize.getTonKho() - size.getTonKho() + size.getSoLuong());
                        size.setTonKho(dtoSize.getTonKho());
                    }
                    size.setTrangThai(true);
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                KichThuoc_TrangPhuc size = KichThuoc_TrangPhuc.builder()
                        .id(new TrangPhuc_KichThuocKey(outfit.getId(), kichThuoc.get().getId()))
                        .trangPhuc(outfit)
                        .kichThuoc(kichThuoc.get())
                        .tonKho(dtoSize.getTonKho())
                        .soLuong(dtoSize.getTonKho())
                        .trangThai(true)
                        .build();
                outfit.getKichThuocTrangPhucs().add(size);
            }
        });
        if (!dto.getDeleteKichThuoc().isEmpty()) deleteOutfitSize(dto.getDeleteKichThuoc(), outfit);
    }

    private String generateOutfitID(Long theLoai, boolean hasPiece) {
        int prefix = hasPiece ? 1 : 0;
        long currentTimeSeconds = Instant.now().getEpochSecond();
        return "TP" + prefix + theLoai + String.format("%05d", currentTimeSeconds % 100000);
    }

    @Transactional
    private void deleteOutfitSize(List<String> kichThuocs, TrangPhuc outfit) {
        kichThuocs.forEach(size -> {
            outfit.getKichThuocTrangPhucs().stream()
                    .filter(kichThuoc -> kichThuoc.getId().getMaKichThuoc().equals(size))
                    .findFirst().orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND))
                    .setTrangThai(false);
        });
    }

    @Override
    @Transactional
    public ResponseMessage updateOutfit(UpdateTrangPhucDTO dto) {
        TrangPhuc outfit = trangPhucRepository.findById(dto.getId())
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        trangPhucMapper.updateEntityFromDto(dto, outfit);
        //Kiểm tra xem có thêm 1 danh mục mới không
        if (!dto.getTheLoai().equals(outfit.getTheLoai().getMaLoai())) {
            TheLoai theLoai = theLoaiRepository.findById(dto.getTheLoai())
                    .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
            // Không thể chuyển 1 trang phục chứa các mảnh trang phục thành đạo cụ được
            if (outfit.isHasPiece() && theLoai.getForAccessary() != null && theLoai.getForAccessary())
                throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE, "Cannot set accessary category for multiple skins");
            outfit.setTheLoai(theLoai);
        }
        //Kiểm tra có thay đổi hình ảnh không
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
            outfit.setHinhAnhs(currentImages);
        }
        if (!dto.getDeleteKichThuoc().isEmpty())
            deleteOutfitSize(dto.getDeleteKichThuoc(), outfit);
        // nếu không có thay đổi thì kích thước sẽ không được request lên
        List<KichThuoc> kichThuocEntity = kichThuocRepository.findAll();
        if (dto.getKichThuocs() != null && !dto.getKichThuocs().isEmpty()) {
            // nếu lúc đầu mà trang phục không có mảnh trang phục mà muốn tách ra làm mảnh trang phục
            // xử lý này nằm trong   if (!dto.getKichThuocs().isEmpty()) là hợp lý
            // vì khi chuyển đổi thì kich thước sẽ bị đổi là NONE,
            // xử lý trên sẽ được kích hoạt
            //TODO:KIỂM TRA LOGIC NÀY DƯỜNG NHƯ SẼ KHÔNG CHẠY
//            if (!outfit.isHasPiece() && !dto.getManhTrangPhucs().isEmpty()) {
//                outfit.setHasPiece(true);
//                Set<TrangPhuc> manhTrangPhucs = dto.getManhTrangPhucs().stream().map(trangPhucMapper::convert).collect(Collectors.toSet());
//                outfit.setManhTrangPhucs(manhTrangPhucs);
//                List<String> thisSize = outfit.getKichThuocTrangPhucs().stream()
//                        .map(item -> item.getId().getMaKichThuoc())
//                        .toList();
//                deleteOutfitSize(thisSize, outfit);
//                AtomicInteger index = new AtomicInteger(0);
//                outfit.getManhTrangPhucs().forEach(item -> {
//                    UpdateTrangPhucDTO mangTrangPhucDTO = dto.getManhTrangPhucs().get(index.get());
//                    item.setId(outfit.getId() + index.get());
//                    item.setTinhTrang(true);
//                    item.setTheLoai(outfit.getTheLoai());
//                    item.setTrangPhucChinh(outfit);
//                    item.setKichThuocTrangPhucs(setOutfitSize(mangTrangPhucDTO, item, kichThuocEntity));
//                    index.getAndIncrement();
//                });
//            }
            //kích thước đang có của outfit muốn update
            Set<KichThuoc_TrangPhuc> currentSizes = outfit.getKichThuocTrangPhucs();
            Set<KichThuoc_TrangPhuc> newSizes = new HashSet<>();
            // Lấy danh sách kích thước mà request lên server
            //request db để lấy entity kichThuoc
            dto.getKichThuocs().forEach(dtoSize -> {
                KichThuoc kichThuoc = kichThuocEntity.stream()
                        .filter(kt -> kt.getId().equals(dtoSize.getMaKichThuoc()))
                        .findFirst()
                        .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
                KichThuoc_TrangPhuc size = currentSizes.stream()
                        .filter(cs -> cs.getKichThuoc().equals(kichThuoc))
                        .findFirst()
                        .orElse(KichThuoc_TrangPhuc.builder()
                                .id(new TrangPhuc_KichThuocKey(outfit.getId(), kichThuoc.getId()))
                                .kichThuoc(kichThuoc)
                                .trangPhuc(outfit)
                                .tonKho(dtoSize.getTonKho())
                                .trangThai(true)
                                .build());
                if (size.getSoLuong() != null) {
                    if (dtoSize.getTonKho() < size.getTonKho() && size.getSoLuong() < size.getTonKho()) {
                        throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE, "Cannot decrease the number of items in stock");
                    } else {
                        size.setSoLuong(dtoSize.getTonKho() - size.getTonKho() + size.getSoLuong());
                        size.setTonKho(dtoSize.getTonKho());
                    }
                } else
                    size.setSoLuong(dtoSize.getTonKho());
                newSizes.add(size);
            });
            outfit.getKichThuocTrangPhucs().addAll(newSizes);
            // Nếu muốn biến trang phục có chứa mảnh trang phục sang trang phục cả bộ thì xóa hết phần tp
        }
        //Từ trang thái có mảnh, sang xóa hết mảnh thì enabled lại các kích thước
        if (!outfit.isHasPiece() && outfit.getManhTrangPhucs() != null && !outfit.getManhTrangPhucs().isEmpty()) {
            outfit.getKichThuocTrangPhucs().forEach(kichThuoc -> {
                if (!kichThuoc.getTrangThai() && !kichThuoc.getId().getMaKichThuoc().equals(SIZE.NONE.name())) {
                    kichThuoc.setTrangThai(true);
                } else {
                    // còn NONE thì set lại false
                    kichThuoc.setTrangThai(false);
                }
            });
//            outfit.setHasPiece(false);
//            outfit.getManhTrangPhucs().forEach(item -> {
//                item.setTinhTrang(false);
//            });
        }
        // Nếu phần trang phục muốn chỉnh sửa
        // không sửa gì thì sẽ empty
        if (outfit.isHasPiece() && !dto.getManhTrangPhucs().isEmpty()) {
            AtomicInteger index = new AtomicInteger(0);
            Set<TrangPhuc> updateOutfit = new HashSet<>();
            //các kích thước cũ phải chuyển trạng thái (logic này khả dụng nếu từ bô sang mảnh trang phục)
            // chỉ dùng cho các kích thước đang trạng thái true (hiểu đơn giản là lần đầu thay đổi logic này sẽ chạy)
            // và trừ kích thước NONE ra vì nếu chuyển sang mảnh trang phục thì NONE này không thể có status false được
            List<String> thisSize = outfit.getKichThuocTrangPhucs().stream()
                    .filter(kichThuoc ->
                            kichThuoc.getTrangThai() && !kichThuoc.getId().getMaKichThuoc().equals(SIZE.NONE.name()))
                    .map(item -> item.getId().getMaKichThuoc())
                    .toList();
            deleteOutfitSize(thisSize, outfit);
            dto.getManhTrangPhucs().forEach(skin -> {
                TrangPhuc manhTrangPhuc = outfit.getManhTrangPhucs().stream()
                        .filter(item -> item.getId().equals(skin.getId()))
                        .findFirst()
                        .orElseGet(() -> TrangPhuc.builder()
                                .id(outfit.getId() + "U" + index.get())
                                .trangPhucChinh((outfit))
                                .theLoai(outfit.getTheLoai())
                                .tenTrangPhuc(skin.getTenTrangPhuc())
                                .giaTien(skin.getGiaTien())
                                .hasPiece(false)
                                .tinhTrang(true)
                                .build());
                manhTrangPhuc.setTenTrangPhuc(skin.getTenTrangPhuc());
                manhTrangPhuc.setGiaTien(skin.getGiaTien());
                if (manhTrangPhuc.getKichThuocTrangPhucs() == null) {
                    manhTrangPhuc.setKichThuocTrangPhucs(setOutfitSize(skin, manhTrangPhuc, kichThuocEntity));
                    updateOutfit.add(manhTrangPhuc);
                } else {
                    //trường hợp này tức là nó có rồi, chỉ là cập nhật lại số lượng gì đó thôi
                    if (!Objects.equals(manhTrangPhuc.getTheLoai().getMaLoai(), outfit.getTheLoai().getMaLoai()))
                        manhTrangPhuc.setTheLoai(outfit.getTheLoai());
                    updateOutfitSize(skin, manhTrangPhuc, kichThuocEntity);
                }
                index.getAndIncrement();
            });
            outfit.getManhTrangPhucs().addAll(updateOutfit);
        }
        if (dto.getDeleteManhTrangPhuc() != null && !dto.getDeleteManhTrangPhuc().isEmpty()) {
            dto.getDeleteManhTrangPhuc().forEach(deleteOutfit -> {
                if (outfit.getManhTrangPhucs() != null)
                    outfit.getManhTrangPhucs().stream().filter(manh -> manh.getId().equals(deleteOutfit)).findFirst()
                            .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND))
                            .setTinhTrang(false);
            });
        }
        TrangPhuc temp = trangPhucRepository.save(outfit);
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
                .data(new HashMap<>() {{
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
                .data(new HashMap<>() {{
                    put("lastest_outfit", trangPhucDTOS);
                }})
                .build();
    }

    @Override
    public ResponseMessage unlockTrangPhuc(String id) {
        TrangPhuc trangPhuc = trangPhucRepository.findById(id)
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        trangPhuc.setTinhTrang(true);
        trangPhucRepository.save(trangPhuc);
        return ResponseMessage.builder()
                .status(OK)
                .message("Unlock outfits successfully")
                .build();
    }

    @Override
    @Transactional
    @ManagerRequired
    public ResponseMessage lockOutfitSize(String size, String id) {
        TrangPhuc_KichThuocKey key = new TrangPhuc_KichThuocKey(id,size);
        KichThuoc_TrangPhuc outfitSize = kichThuocTrangPhucRepository.findById(key)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        outfitSize.setTrangThai(false);
        return ResponseMessage.builder()
                .status(OK)
                .message("Lock outfits size successfully")
                .build();
    }
    @Override
    @Transactional
    @ManagerRequired
    public ResponseMessage unlockOutfitSize(String size, String id) {
        TrangPhuc_KichThuocKey key = new TrangPhuc_KichThuocKey(id,size);
        KichThuoc_TrangPhuc outfitSize = kichThuocTrangPhucRepository.findById(key)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        outfitSize.setTrangThai(true);
        return ResponseMessage.builder()
                .status(OK)
                .message("Lock outfits size successfully")
                .build();
    }

    @Override
    @Transactional
    @ManagerRequired
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
    @ManagerRequired
    public ResponseMessage deleteTrangPhuc(List<String> ids) {
        trangPhucRepository.deleteAllById(ids);
        return ResponseMessage.builder()
                .status(OK)
                .message("Delete outfit successfully")
                .build();
    }

    @Override
    @Transactional
    @ManagerRequired
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
