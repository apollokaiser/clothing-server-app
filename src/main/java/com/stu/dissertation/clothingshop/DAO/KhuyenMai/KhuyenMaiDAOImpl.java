package com.stu.dissertation.clothingshop.DAO.KhuyenMai;

import com.stu.dissertation.clothingshop.DTO.KhuyenMaiThanhToanDTO;
import com.stu.dissertation.clothingshop.Entities.KhuyenMai;
import com.stu.dissertation.clothingshop.Mapper.KhuyenMaiMapper;
import com.stu.dissertation.clothingshop.Repositories.KhuyenMaiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class KhuyenMaiDAOImpl implements KhuyenMaiDAO{

    private final KhuyenMaiRepository khuyenMaiRepository;

    private final KhuyenMaiMapper khuyenMaiMapper;

    @Override
    public Optional<KhuyenMai> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public KhuyenMai save(KhuyenMai entity) {
        return null;
    }

    @Override
    public KhuyenMai update(KhuyenMai entity) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public List<KhuyenMai> getKhuyenMais(Long id) {
        return List.of();
    }

    @Override
    public List<KhuyenMai> getKhuyenMais() {
        return List.of();
    }

    @Override
    @Transactional
    public List<KhuyenMaiThanhToanDTO> getKhuyenMaisThanhToan() {
        Set<KhuyenMai> khuyenMais = khuyenMaiRepository.getUnGroupPromotions();
        return khuyenMais.stream().map(khuyenMaiMapper::convertToPromotionPayment).collect(Collectors.toList());
    }
}
