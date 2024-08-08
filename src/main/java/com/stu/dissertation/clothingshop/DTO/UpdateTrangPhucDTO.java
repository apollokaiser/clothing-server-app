package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTrangPhucDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    private String tenTrangPhuc;
    private BigDecimal giaTien;
    private String moTa;
    private Long theLoai;
    private boolean hasPiece;
    private List<String> hinhAnhs;
    private List<UpdateTrangPhucDTO> manhTrangPhucs;
    private List<KichThuocTrangPhucDTO> kichThuocs;
    private List<String> deleteManhTrangPhuc;
    private List<String> deleteKichThuoc;
}
