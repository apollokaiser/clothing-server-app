package com.stu.dissertation.clothingshop.Service.TaiKhoanLienKet;

import com.stu.dissertation.clothingshop.Entities.TaiKhoanLienKet;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Payload.Response.UserInfo;

import java.util.Optional;

public interface TaiKhoanLKService {
    Optional<TaiKhoanLienKet> findById(String id);
    TaiKhoanLienKet save(TaiKhoanLienKet externalUserAccount);
    Optional<TaiKhoanLienKet> findAccountByEmail(String email);
    ResponseMessage proccess(UserInfo externalUserAccount);
}
