package com.stu.dissertation.clothingshop.Service.TheLoai;

import com.stu.dissertation.clothingshop.Entities.TheLoai;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;

import java.util.List;

public interface TheLoaiService {
    ResponseMessage getTheLoai();
    ResponseMessage getTheLoaiPromotion();
//    List<TheLoai> buildHierarchy();
}
