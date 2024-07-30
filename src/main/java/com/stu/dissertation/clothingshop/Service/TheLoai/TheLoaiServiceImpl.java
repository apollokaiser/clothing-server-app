package com.stu.dissertation.clothingshop.Service.TheLoai;

import com.stu.dissertation.clothingshop.Cache.CacheService.TheLoai.TheLoaiRedisService;
import com.stu.dissertation.clothingshop.DTO.TheLoaiDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.Entities.TheLoai;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import com.stu.dissertation.clothingshop.Mapper.TheLoaiMapper;
import com.stu.dissertation.clothingshop.Mapper.TrangPhucMapper;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.TheLoaiRepository;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Slf4j
public class TheLoaiServiceImpl implements TheLoaiService{
    private final TheLoaiRepository theLoaiRepository;
    private final TrangPhucRepository trangPhucRepository;
    private final TrangPhucMapper trangPhucMapper;
    private final TheLoaiMapper theLoaiMapper;
    private final TheLoaiRedisService theLoaiRedisService;
    @Override
    @Transactional
    public ResponseMessage getTheLoai() {
        ResponseMessage response = ResponseMessage.builder()
                .status(OK)
                .message("Get cateogies successfully")
                .build();
        List<TheLoaiDTO> categories = theLoaiRedisService.getTheLoai();
        if(categories == null) {
            List<TheLoai> theLoais = theLoaiRepository.findAll();
            if (theLoais.isEmpty()) return null;
            List<TheLoaiDTO> dto = theLoais.stream()
                    .filter(item -> item.getParent() == null)
                    .map(theLoaiMapper::convert)
                    .toList();
            //update into redis
            theLoaiRedisService.updateTheLoai(dto);
            response.setData(new HashMap<>(){{
                put("theloais", dto);
            }});
        } else {
            response.setData(new HashMap<>(){{
                put("theloais", categories);
            }});
        }
        return response;
    }
    @Override
    @Transactional
    public ResponseMessage getTrangPhucByCategory(Long category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Long> ids = getAllDescendantCategoryIds(category);
        ids.add(category);
        List<TrangPhuc> list = trangPhucRepository.getTrangPhucByCategory(ids, pageable);
        List<TrangPhucDTO> trangPhucDTOS = list.stream().map(trangPhucMapper::convert).toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("Get outfit by category successfully")
                .data(new HashMap<>(){{
                    put("trangphucs", trangPhucDTOS);
                }})
                .build();
    }
    private List<Long> getAllDescendantCategoryIds(Long parentId) {
        List<Long> categoryIds =new ArrayList<>();
        collectDescendantCategoryIds(parentId, categoryIds);
        return categoryIds;
    }

    @Transactional
    private void collectDescendantCategoryIds(Long parentId, List<Long> categoryIds) {
        List<TheLoai> children = theLoaiRepository.findByParentId(parentId);
        for (TheLoai child : children) {
            categoryIds.add(child.getMaLoai());
            collectDescendantCategoryIds(child.getMaLoai(), categoryIds);
        }
    }
}
