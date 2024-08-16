package com.stu.dissertation.clothingshop.Payload.Request;

import com.stu.dissertation.clothingshop.DTO.KhuyenMaiDTO;

import java.util.List;

/***
 * @author thinh
 * @param khuyenMai thông tin khuyến mãi
 * @param ids danh sách các mã thể loại
 */
public record AddpromotionRequest(
        KhuyenMaiDTO khuyenMai,
        List<Long> ids,
        List<Long> deleteIds
) {
}
