package com.stu.dissertation.clothingshop.Service.PhieuKhuyenMai;

import com.stu.dissertation.clothingshop.DAO.PhieukhuyenMai.PhieuKhuyenMaiDAO;
import com.stu.dissertation.clothingshop.Entities.PhieuKhuyenMai;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PhieuKhuyenMaiSetviceImpl implements PhieuKhuyenMaiService{

    private final PhieuKhuyenMaiDAO phieuKhuyenMaiDAO;
    @Override
    public ResponseMessage checkPhieuKhuyenMai(String id) {
        PhieuKhuyenMai phieuKhuyenMai = phieuKhuyenMaiDAO.findById(id)
                .orElseThrow(()-> new ApplicationException(BusinessErrorCode.INVALID_PROMOTION_CODE));
        boolean isValidCode = false;
       if(isBetweenTime(phieuKhuyenMai.getNgayBatDau(), phieuKhuyenMai.getNgayKetThuc())) {
           if (phieuKhuyenMai.isKhachMoi()) {
               isValidCode = true;
           }
           if (phieuKhuyenMai.getSoLuongNhap() > 0) {
               isValidCode = true;
           }
       }
       if(!isValidCode) {
          throw new ApplicationException(BusinessErrorCode.EXPIRED_PROMOTION_CODE);
       } else{
           return ResponseMessage.builder()
                   .status(HttpStatus.OK)
                   .message("Get promotion ticket successfully")
                   .data(new HashMap<>(){{
                       put("phieuKhuyenMai", phieuKhuyenMai);
                   }})
                   .build();
       }
    }
    private boolean isBetweenTime(long startTime, long endTime) {
        long thisTime = Instant.now().getEpochSecond();
        return thisTime >= startTime && thisTime <= endTime;
    }
}
