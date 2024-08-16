package com.stu.dissertation.clothingshop.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCategoryDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String tenLoai;
    private String moTa;
    private Boolean forAccessary;
    private Long parentId;
    public String toSlug() {
        if (this.tenLoai == null || this.tenLoai.isEmpty()) {
            return "";
        }
        // Chuyển đổi ký tự đặc biệt và dấu tiếng Việt thành ký tự không dấu
        String normalized = Normalizer.normalize(this.tenLoai, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String withoutDiacritics = pattern.matcher(normalized).replaceAll("");

        // Thay thế các ký tự không phải là chữ cái hoặc số bằng dấu gạch ngang
        String slug = withoutDiacritics
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // Xóa các ký tự không phải chữ cái, số hoặc khoảng trắng
                .trim()
                .replaceAll("[\\s-]+", "-"); // Thay thế khoảng trắng và dấu gạch ngang liên tiếp bằng một dấu gạch ngang
        return slug;
    }
}
