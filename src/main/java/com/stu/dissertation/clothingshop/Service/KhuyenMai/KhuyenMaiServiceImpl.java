package com.stu.dissertation.clothingshop.Service.KhuyenMai;

import com.stu.dissertation.clothingshop.DAO.KhuyenMai.KhuyenMaiDAO;
import com.stu.dissertation.clothingshop.DTO.KhuyenMaiThanhToanDTO;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class KhuyenMaiServiceImpl implements KhuyenMaiService{
    private final KhuyenMaiDAO khuyenMaiDAO;
    @Override
    @Transactional
    public ResponseMessage getKhuyenMaiThanhToan() {
        List<KhuyenMaiThanhToanDTO> khuyenMais = khuyenMaiDAO.getKhuyenMaisThanhToan();
        return ResponseMessage.builder()
                .status(OK)
                .message("Get khuyen mai thannh toan successfully")
                .data(new HashMap<>(){{
                    put("khuyenmai_thannhtoans", khuyenMais);
                }})
                .build();
    }
}
