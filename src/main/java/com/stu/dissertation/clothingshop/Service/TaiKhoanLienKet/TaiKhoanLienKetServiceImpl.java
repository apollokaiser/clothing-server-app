package com.stu.dissertation.clothingshop.Service.TaiKhoanLienKet;

import com.stu.dissertation.clothingshop.DAO.TaiKhoanLienKet.TaiKhoanLienKetDAO;
import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Payload.Response.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaiKhoanLienKetServiceImpl implements TaiKhoanLKService{

    private final TaiKhoanLienKetDAO taiKhoanLienKetDAO;

    @Override
    public Optional<TaiKhoanLienKet> findById(String id) {
        return taiKhoanLienKetDAO.findById(id);
    }

    @Override
    public TaiKhoanLienKet save(TaiKhoanLienKet externalUserAccount) {
        return null;
    }

    @Override
    public Optional<TaiKhoanLienKet> findAccountByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public ResponseMessage proccess(UserInfo externalUserAccount) {
        return null;
    }
}
