package com.stu.dissertation.clothingshop.Service.KhuyenMai;

import com.stu.dissertation.clothingshop.Payload.Request.AddpromotionRequest;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;

public interface KhuyenMaiService {
    ResponseMessage getKhuyenMaiThanhToan();

    ResponseMessage savePromotion(AddpromotionRequest promotion);

    ResponseMessage deletePromotion(Long id);

    ResponseMessage getPromotionList();

    ResponseMessage updatePromotion(AddpromotionRequest promotion);

    ResponseMessage getCategoryInPromotion(Long id);
    ResponseMessage getPromotionsCategory();
}
