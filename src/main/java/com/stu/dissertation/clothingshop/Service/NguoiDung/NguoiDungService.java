package com.stu.dissertation.clothingshop.Service.NguoiDung;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.stu.dissertation.clothingshop.Cache.CacheService.GioHang.GioHangRedisService;
import com.stu.dissertation.clothingshop.Cache.CacheService.InvalidToken.InvalidTokenRedisService;
import com.stu.dissertation.clothingshop.DAO.NguoiDung.NguoiDungDAO;
import com.stu.dissertation.clothingshop.DTO.NguoiDungDetailDTO;
import com.stu.dissertation.clothingshop.Entities.*;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.LoginType;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Payload.Request.*;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Payload.Response.UserInfo;
import com.stu.dissertation.clothingshop.Repositories.DiaChiRepository;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Security.JWTAuth.JWTService;
import com.stu.dissertation.clothingshop.Service.ExternalIdentity.GoogleIdentityService;
import com.stu.dissertation.clothingshop.Service.GioHang.GioHangService;
import com.stu.dissertation.clothingshop.Service.RefreshToken.RefreshTokenService;
import com.stu.dissertation.clothingshop.Service.UserToken.UserTokenService;
import com.stu.dissertation.clothingshop.Utils.RandomCodeGenerator;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.*;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class NguoiDungService {
    private final JWTService jwtService;
    private final NguoiDungDAO nguoiDungDAO;
    private final UserTokenService userTokenService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final GoogleIdentityService googleIdentityService;
    private final PasswordEncoder passwordEncoder;
    private final DiaChiRepository diaChiRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final InvalidTokenRedisService invalidTokenRedisService;
    private final GioHangRedisService gioHangRedisService;

    public ResponseMessage activateAccount(String token) {
        try {
            return userTokenService.validateUserToken(token);
        } catch (MessagingException e) {
            throw new ApplicationException((BusinessErrorCode.ERROR_MAIL));
        }
    }

    @Transactional
    public ResponseMessage loginWithAccount(UserCredentialsRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        NguoiDung user = (NguoiDung) auth.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.handle(user, user.getRefreshToken());
        Long authTime = Instant.now().getEpochSecond();
        nguoiDungRepository.updatelastLogin(authTime, user.getEmail());
        try {
            String accessToken = jwtService.generateToken(user);
            return ResponseMessage.builder()
                    .status(OK)
                    .message("Login successfully")
                    .data(new HashMap<>() {{
                        put("access_token", accessToken);
                        put("refresh_token", refreshToken.getRefreshToken());
                    }})
                    .build();
        } catch (JOSEException e) {
            throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR, "Error generating token");
        }
    }
    @Transactional
    public ResponseMessage refreshToken(String token) {
        RefreshToken refresh_token = refreshTokenService.findByToken(token)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.INVALID_REFRESH_TOKEN));
        if (new Date(refresh_token.getExpiresAt()).before(new Date())) {
            throw new ApplicationException(BusinessErrorCode.EXPIRED_REFRESH_TOKEN);
        } else {
            try {
                String accessToken = jwtService.generateToken(refresh_token.getNguoiDung());
                return ResponseMessage.builder()
                        .status(OK)
                        .message("Get new access token successfully")
                        .data(new HashMap<>() {{
                            put("access_token", accessToken);
                        }})
                        .build();
            } catch (JOSEException e) {
                throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR, "Error generating token");
            }
        }
    }

    public ResponseMessage getUserInfo(String uid) {
        NguoiDungDetailDTO user = nguoiDungDAO.findUserById(uid);
        return ResponseMessage.builder()
                .status(OK)
                .message("Get user info successfully")
                .data(new HashMap<>() {{
                    put("user_info", user);
                }})
                .build();
    }

    public ResponseMessage resetPassword(String email) throws MessagingException {
        NguoiDung nguoiDung = nguoiDungDAO.findNguoiDungByEmail(email)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND));
        if(nguoiDung.getMatKhau() == null)
            throw new ApplicationException(BusinessErrorCode.USER_NOT_FOUND);
        String token = RandomCodeGenerator.generateRandomCode(7);
        UserToken userToken = UserToken.builder()
                .token(token)
                .nguoiDung(nguoiDung)
                .build();
        userTokenService.saveUserToken(userToken);
        return ResponseMessage.builder()
                .status(OK)
                .message("Your request was successfully")
                .build();
    }

    @Transactional
    public ResponseMessage resetPassword(RePasswordRequest request) {
        UserToken userToken = userTokenService.findByToken(request.getResetCode());
        NguoiDung nguoiDung = userToken.getNguoiDung();
        nguoiDungDAO.resetPassword(nguoiDung.getEmail(), request.getNewPassword());
        userTokenService.validateResetPasswordToken(request.getResetCode());
        return ResponseMessage.builder()
                .status(OK)
                .message("Reset password successfully")
                .build();
    }

    @Transactional
    public ResponseMessage loginWithGoogle(String authCode) {
        UserInfo userInfo = googleIdentityService.exchangeUserInfo(authCode);
        Optional<NguoiDung> nguoiDung = nguoiDungRepository.findByEmail(userInfo.email());
        //người dùng chưa từng có account bằng email này trước đây --> tạo tài khoản mới
        if (nguoiDung.isEmpty()) {
            return signupWithGoogle(userInfo);
        } else {
            if (Objects.equals(nguoiDung.get().getId(), userInfo.id())) {
                RefreshToken refreshToken = refreshTokenService.handle(nguoiDung.get(), nguoiDung.get().getRefreshToken());
                Long authTime = Instant.now().getEpochSecond();
                nguoiDungRepository.updatelastLogin(authTime, nguoiDung.get().getEmail());
                try {
                    String accesstoken = jwtService.generateToken(nguoiDung.get());
                    return ResponseMessage.builder()
                            .status(OK)
                            .message("Login successfully")
                            .data(new HashMap<>() {{
                                put("access_token", accesstoken);
                                put("refresh_token", refreshToken.getRefreshToken());
                            }})
                            .build();
                } catch (JOSEException e) {
                    throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR);
                }
            }
            throw new ApplicationException(BusinessErrorCode.EMAIL_REGISTERED);
        }
    }

    @Transactional
    private ResponseMessage signupWithGoogle(UserInfo userInfo) {
        // tạo người dùng
        NguoiDung userEntity = NguoiDung.builder()
                .id(userInfo.id())
                .email(userInfo.email())
                .tenNguoiDung(userInfo.name())
                .enabled(true)
                .khachMoi(true)
                .build();
        //tạo tài khoản
        TaiKhoanLienKet taiKhoanLienKet = TaiKhoanLienKet.builder()
                .nguoiDung(userEntity)
                .provider(LoginType.GOOGLE)
                .build();
        //liên kết người dùng và tài khoản
        userEntity.setTaiKhoan(taiKhoanLienKet);
        userEntity.setLastLogin(Instant.now().getEpochSecond());
        //lưu người dùng và tài khoản
        NguoiDung nguoiDung = nguoiDungDAO.save(userEntity);
        //tạo refresh token
        RefreshToken refreshToken = refreshTokenService.handle(nguoiDung, null);
        try {
            //tạo access token
            String accessToken = jwtService.generateToken(nguoiDung);
            return ResponseMessage.builder()
                    .status(HttpStatus.OK)
                    .message("Login successfully")
                    .data(new HashMap<>() {{
                        put("access_token", accessToken);
                        put("refresh_token", refreshToken.getRefreshToken());
                    }})
                    .build();
        } catch (JOSEException e) {
            throw new ApplicationException(BusinessErrorCode.ACCESS_TOKEN_ERROR);
        }

    }

    @Transactional
    public ResponseMessage changePassword(RePasswordRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
               .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getOldPassword(), nguoiDung.getMatKhau())) {
            throw new ApplicationException(BusinessErrorCode.WRONG_PASSWORD);
        }
        if(passwordEncoder.matches(request.getNewPassword(), nguoiDung.getPassword())) {
            throw new ApplicationException(BusinessErrorCode.NO_CHANGE_APPLY);
        }
        nguoiDung.setMatKhau(passwordEncoder.encode(request.getNewPassword()));
        nguoiDungRepository.save(nguoiDung);
        return ResponseMessage.builder()
               .status(OK)
               .message("Change password successfully")
               .build();
    }
    @Transactional
    public ResponseMessage changeInfo(UpdateUserRequest update, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        if(!Objects.equals(email, update.getEmail())) {
            throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE);
        }
        NguoiDung nguoiDung =nguoiDungRepository.findByEmail(email)
               .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND));
        if(update.getName()!=null && !update.getName().isEmpty())
            nguoiDung.setTenNguoiDung(update.getName());
        if(update.getPhone()!=null && !update.getPhone().isEmpty()) {
            nguoiDung.setSdt(update.getPhone());
        }
      NguoiDung user =  nguoiDungRepository.save(nguoiDung);
        String jwtHeader = request.getHeader("Authorization").substring(7);
        // lưu invalid token vào redis
        JWTClaimsSet jwt = jwtService.verifiedToken(jwtHeader);
        invalidTokenRedisService.addToken(jwt.getJWTID(), jwt.getExpirationTime().getTime());
        try {
            String accessToken = jwtService.generateToken(user);
        return ResponseMessage.builder()
               .status(OK)
               .message("Change info successfully")
                .data(new HashMap<>(){{
                    put("access_token", accessToken);
                }})
               .build();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public ResponseMessage addAddress(AddressRequest address) {
     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     String email = auth.getName();
     NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
             .orElseThrow(()-> new ApplicationException((BusinessErrorCode.USER_NOT_FOUND)));
     DiaChi diaChi = DiaChi.builder()
             .nguoiDung(nguoiDung)
             .tenDiaChi(address.name())
             .diaChi(address.address())
             .isDefault(address.isDefault())
             .build();
     AddressDetail detail = AddressDetail.builder()
             .wardId(address.wardId())
             .districtId(address.districtId())
             .provinceId(address.provinceId())
             .diaChi(diaChi)
             .build();
     diaChi.setDetail(detail);
     if(address.isDefault()) {
         diaChiRepository.resetDefaultAddress(nguoiDung.getId());
     }
     DiaChi saveAddress  = diaChiRepository.save(diaChi);
     return ResponseMessage.builder()
             .status(OK)
             .message("Add address successfully")
             .data(new HashMap<>(){{
                 put("address", saveAddress);
             }})
             .build();
    }

    public ResponseMessage deleteAddress(Long id) {
        diaChiRepository.deleteById(id);
        return ResponseMessage.builder()
                .status(OK)
                .message("Delete address successfully")
                .build();
    }

    @Transactional
    public ResponseMessage updateAddress(AddressRequest address, boolean updateDefault) {
        DiaChi diaChi = diaChiRepository.findById(address.id())
               .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        diaChi.setTenDiaChi(address.name());
        diaChi.setDiaChi(address.address());
        //có sự thay đổi về địa chỉ mặc định
        if(!diaChi.getIsDefault() && address.isDefault()) {
            diaChiRepository.resetDefaultAddress(diaChi.getNguoiDung().getId());
        } if(diaChi.getIsDefault() && !address.isDefault()){
            diaChi.setIsDefault(false);
        }
        if(!updateDefault){
        diaChi.getDetail().setProvinceId(address.provinceId());
        diaChi.getDetail().setDistrictId(address.districtId());
        diaChi.getDetail().setWardId(address.wardId());
        }
        diaChiRepository.save(diaChi);
        return ResponseMessage.builder()
               .status(OK)
               .message("Update address successfully")
               .build();
    }
    public ResponseMessage updateAdress(Long id, boolean setDefault) {
        DiaChi diaChi = diaChiRepository.findById(id)
               .orElseThrow(() -> new ApplicationException(BusinessErrorCode.NOT_FOUND));
        diaChi.setIsDefault(setDefault);
        diaChiRepository.save(diaChi);
        return ResponseMessage.builder()
               .status(OK)
               .message("Update address successfully")
               .build();
    }
    private String getUID () throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
        String jwt = oauthToken.getToken().getTokenValue();
        return jwtService.extractUID(jwt);
    }
    public void logout(String accessToken) throws ParseException {
        gioHangRedisService.clearPreOrder(getUID());
        JWTClaimsSet jwt = jwtService.verifiedToken(accessToken);
         String jwtID = jwt.getJWTID();
         Long jwtExp = jwt.getExpirationTime().getTime();
        invalidTokenRedisService.addToken(jwtID,jwtExp);
    }
}
