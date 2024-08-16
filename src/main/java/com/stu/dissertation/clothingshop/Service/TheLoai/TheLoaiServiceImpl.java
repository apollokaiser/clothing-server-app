package com.stu.dissertation.clothingshop.Service.TheLoai;

import com.stu.dissertation.clothingshop.Cache.CacheService.TheLoai.TheLoaiRedisService;
import com.stu.dissertation.clothingshop.DTO.TheLoaiDTO;
import com.stu.dissertation.clothingshop.DTO.TrangPhucDTO;
import com.stu.dissertation.clothingshop.DTO.UpdateCategoryDTO;
import com.stu.dissertation.clothingshop.Entities.TheLoai;
import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.TheLoaiMapper;
import com.stu.dissertation.clothingshop.Mapper.TrangPhucMapper;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.TheLoaiRepository;
import com.stu.dissertation.clothingshop.Repositories.TrangPhucRepository;
import com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation.AdminRequired;
import com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation.HighestRequired;
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

    @Override
    @Transactional
    @AdminRequired
    public ResponseMessage insertCategory(UpdateCategoryDTO category) {
        TheLoai theLoai = TheLoai.builder()
                .tenLoai(category.getTenLoai())
                .moTa(category.getMoTa())
                .slug(category.toSlug())
                .forAccessary(category.getForAccessary())
                .build();
        if (category.getParentId() != null){
            TheLoai parent = theLoaiRepository.findById(category.getParentId())
                    .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
            theLoai.setParent(parent);
    }
        TheLoai saved = theLoaiRepository.save(theLoai);
        TheLoaiDTO theLoaiDTO = theLoaiMapper.convert(saved);
        theLoaiRedisService.clearTheLoai();
        return ResponseMessage.builder()
                .status(OK)
                .message("Save category successfully")
                .data(new HashMap<>(){{
                    put("theloai", theLoaiDTO);
                }})
                .build();
    }

    @Override
    @HighestRequired
    @Transactional
    public ResponseMessage deleteCategory(Long id, boolean deleteAll) {
        if(deleteAll)
            theLoaiRepository.deleteByParentId(id);
        theLoaiRepository.deleteById(id);
        theLoaiRedisService.clearTheLoai();
        return ResponseMessage.builder()
                .status(OK)
                .message("Delete category successfully")
                .build();
    }

    @Override
    @AdminRequired
    @Transactional
    public ResponseMessage updateCategory(UpdateCategoryDTO category) {
        if(category.getId() ==null) throw new ApplicationException(BusinessErrorCode.NOT_FOUND);
        TheLoai theLoai = theLoaiRepository.findById(category.getId())
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        if(category.getParentId() !=null) {
            TheLoai parent = theLoaiRepository.findById(category.getParentId())
                    .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
            theLoai.setParent(parent);
        }
            theLoai.setTenLoai(category.getTenLoai());
            theLoai.setMoTa(category.getMoTa());
            theLoai.setSlug(category.toSlug());
        TheLoai saved = theLoaiRepository.save(theLoai);
        TheLoaiDTO theLoaiDTO = theLoaiMapper.convert(saved);
        theLoaiRedisService.clearTheLoai();
            return ResponseMessage.builder()
                   .status(OK)
                   .message("Update category successfully")
                    .data(new HashMap<>(){{
                         put("theloai", theLoaiDTO);
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
