package com.stu.dissertation.clothingshop.Service;

import com.stu.dissertation.clothingshop.Config.TestSecurityConfig;
import com.stu.dissertation.clothingshop.DTO.KichThuocTrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.UpdateTrangPhucDTO;
import com.stu.dissertation.clothingshop.Entities.*;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.TrangPhucMapper;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.KichThuocRepository;
import com.stu.dissertation.clothingshop.Repositories.KichThuocTrangPhucRepository;
import com.stu.dissertation.clothingshop.Repositories.TheLoaiRepository;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import com.stu.dissertation.clothingshop.Service.TrangPhuc.TrangPhucService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Slf4j
@TestPropertySource("/test.properties")
@ContextConfiguration(classes = {TestSecurityConfig.class})
public class TrangPhucServiceTest {

    @Autowired
    private TrangPhucService trangPhucService;
    @SpyBean
    private TrangPhucMapper trangPhucMapper;
    @MockBean
    private TheLoaiRepository theLoaiRepository;
    @MockBean
    private KichThuocRepository kichThuocRepository;
    @MockBean
    private TrangPhucRepository trangPhucRepository;
    @MockBean
    private KichThuocTrangPhucRepository kichThuocTrangPhucRepository;

    private static UpdateTrangPhucDTO updateTrangPhucDTO;

    private static TrangPhuc trangPhuc;
    private static ResponseMessage responseMessage;
    private static TheLoai theLoai;
    private static TheLoai category_accessary;
    private static List<KichThuoc> kichThuocs;

    @BeforeAll
    public static void setup() {
        updateTrangPhucDTO = UpdateTrangPhucDTO.builder()
                .hasPiece(false)
                .tinhTrang(true)
                .theLoai(1L)
                .tinhTrang(true)
                .kichThuocs(Collections.singletonList(KichThuocTrangPhucDTO.builder()
                        .maKichThuoc("S")
                        .soLuong(5)
                        .build()))
                .hinhAnhs(Arrays.asList("test1.jpg", "test2.jpg"))
                .build();
        theLoai = TheLoai.builder()
                .maLoai(1L)
                .build();
        category_accessary = TheLoai.builder()
                .maLoai(2L)
                .forAccessary(true)
                .build();
        kichThuocs = new ArrayList<>(){{
            add(KichThuoc.builder().id("S").build());
            add(KichThuoc.builder().id("L").build());
        }};
        responseMessage = new ResponseMessage();
    }
    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "userDetailsService")
    public void test_priority_addTrangPhuc_withUser() {
        log.info("test_priority_addTrangPhuc_withUser is running");
        assertThrows(AccessDeniedException.class,
                () -> trangPhucService.addTrangPhuc(updateTrangPhucDTO));
    }

    @Test
    @WithUserDetails(value = "promotionStaff", userDetailsServiceBeanName = "userDetailsService")
    public void test_priority_addTrangPhuc_withPromotionStaff() {
        assertThrows(AccessDeniedException.class,
                () -> trangPhucService.addTrangPhuc(updateTrangPhucDTO));
    }

    @Test
    @WithUserDetails(value = "outfitStaff", userDetailsServiceBeanName = "userDetailsService")
    public void test_priority_addTrangPhuc_withValidAdmin_success() {
        //may use superaccount or manager to test
       setData();
        Mockito.when(theLoaiRepository.findById(any())).thenReturn(Optional.of(theLoai));
        Mockito.when( kichThuocRepository.findAll()).thenReturn(kichThuocs);
        Mockito.when(trangPhucMapper.convert(updateTrangPhucDTO)).thenReturn(trangPhuc);
        responseMessage = trangPhucService.addTrangPhuc(updateTrangPhucDTO);
        Assertions.assertThat(responseMessage.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    @Test
    @WithUserDetails(value = "super", userDetailsServiceBeanName = "userDetailsService")
    public void test__addTrangPhuc_NoImage_fail() {
        trangPhuc = new TrangPhuc();
        Mockito.when(theLoaiRepository.findById(any())).thenReturn(Optional.of(theLoai));
        Mockito.when( kichThuocRepository.findAll()).thenReturn(kichThuocs);
        Mockito.when(trangPhucMapper.convert(updateTrangPhucDTO)).thenReturn(trangPhuc);
        assertThrows(ApplicationException.class, ()->
                trangPhucService.addTrangPhuc(updateTrangPhucDTO));
    }@Test
    @WithUserDetails(value = "super", userDetailsServiceBeanName = "userDetailsService")
    public void test__addTrangPhuc_NoSize_withAccessaryHasBeenChoosen_fail() {
        setData();
        updateTrangPhucDTO.setTheLoai(2L);
        updateTrangPhucDTO.setKichThuocs(null);
        Mockito.when(theLoaiRepository.findById(2L)).thenReturn(Optional.of(category_accessary));
        Mockito.when( kichThuocRepository.findAll()).thenReturn(kichThuocs);
        Mockito.when(trangPhucMapper.convert(updateTrangPhucDTO)).thenReturn(trangPhuc);
        var exception = assertThrows(ApplicationException.class, ()->
                trangPhucService.addTrangPhuc(updateTrangPhucDTO));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Must have at least one size");
    }
    @Test
    @WithUserDetails(value = "super", userDetailsServiceBeanName = "userDetailsService")
    public void test__addTrangPhuc_HaveSize_withOutfitPiecesHaveBeenChoosen_fail() {
        //GIVEN
        // Cannot have either outfitsize or outfitpiece
        UpdateTrangPhucDTO updateData = setOutfitPieceData();
        //set size for updateData to test
        updateData.setKichThuocs(updateTrangPhucDTO.getKichThuocs());
        TrangPhuc outfit = trangPhucMapper.convert(updateData);
        //WHEN
        Mockito.when(theLoaiRepository.findById(1L)).thenReturn(Optional.of(theLoai));
        Mockito.when( kichThuocRepository.findAll()).thenReturn(kichThuocs);
        Mockito.when(trangPhucMapper.convert(updateData)).thenReturn(outfit);
        var exception = assertThrows(ApplicationException.class, ()->
                trangPhucService.addTrangPhuc(updateData));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Cannot set outfit size for outfit that had pieces");
    }
    private void setData() {
        Set<HinhAnhTrangPhuc> hinhAnhs = new HashSet<>(){{
            add(new HinhAnhTrangPhuc("test1.jpg"));
            add(new HinhAnhTrangPhuc("test2.jpg"));
        }};
        trangPhuc = TrangPhuc.builder()
                .tinhTrang(true)
                .hinhAnhs(hinhAnhs)
                .hasPiece(true)
                .build();
    }
    private UpdateTrangPhucDTO setOutfitPieceData() {
        UpdateTrangPhucDTO piece1 = UpdateTrangPhucDTO.builder()
                .hasPiece(false)
                .tinhTrang(true)
                .theLoai(1L)
                .tinhTrang(true)
                .kichThuocs(Collections.singletonList(KichThuocTrangPhucDTO.builder()
                        .maKichThuoc("S")
                        .soLuong(5)
                        .build()))
                .build();
        UpdateTrangPhucDTO piece2 = UpdateTrangPhucDTO.builder()
                .hasPiece(false)
                .tinhTrang(true)
                .theLoai(1L)
                .tinhTrang(true)
                .kichThuocs(Collections.singletonList(KichThuocTrangPhucDTO.builder()
                        .maKichThuoc("L")
                        .soLuong(4)
                        .build()))
                .build();
        return UpdateTrangPhucDTO.builder()
                .hasPiece(true)
                .tinhTrang(true)
                .theLoai(1L)
                .tinhTrang(true)
                .hinhAnhs(Arrays.asList("test1.jpg", "test2.jpg"))
                .manhTrangPhucs(new ArrayList<>(){{
                    add(piece1);
                    add(piece2);
                }})
                .build();
    }
}
