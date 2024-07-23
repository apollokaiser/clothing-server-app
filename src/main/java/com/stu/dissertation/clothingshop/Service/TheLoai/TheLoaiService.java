package com.stu.dissertation.clothingshop.Service.TheLoai;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;

public interface TheLoaiService {
    ResponseMessage getTheLoai();
    ResponseMessage getTrangPhucByCategory(Long category, int page, int size);
}
