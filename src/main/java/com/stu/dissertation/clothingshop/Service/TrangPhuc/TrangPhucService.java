package com.stu.dissertation.clothingshop.Service.TrangPhuc;

import com.stu.dissertation.clothingshop.Entities.TrangPhuc;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrangPhucService {
    ResponseMessage getTrangPhuc(Pageable pageable);
    ResponseMessage getTrangPhucDetails(String id);
}
