package com.stu.dissertation.clothingshop.Service.Staff;

import com.stu.dissertation.clothingshop.DTO.AdminStaffDTO;
import com.stu.dissertation.clothingshop.Entities.AddressDetail;
import com.stu.dissertation.clothingshop.Entities.DiaChi;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Entities.Role;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Enum.EmailTemplateEngine;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Mapper.DiaChiMapper;
import com.stu.dissertation.clothingshop.Mapper.NguoiDungMapper;
import com.stu.dissertation.clothingshop.Payload.Request.AdminInfo;
import com.stu.dissertation.clothingshop.Payload.Request.UpdateAdminInfo;
import com.stu.dissertation.clothingshop.Payload.Response.ResponseMessage;
import com.stu.dissertation.clothingshop.Repositories.NguoiDungRepository;
import com.stu.dissertation.clothingshop.Repositories.RoleRepository;
import com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation.AdminRequired;
import com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation.HighestRequired;
import com.stu.dissertation.clothingshop.Security.AuthorizeAnnotation.ManagerRequired;
import com.stu.dissertation.clothingshop.Service.EmailService.EmailService;
import com.stu.dissertation.clothingshop.Utils.UIDCreator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.stu.dissertation.clothingshop.Enum.ROLE.HIGHEST_ROLE;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class StaffServiceImpl implements StaffService {
    private final NguoiDungRepository nguoiDungRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final DiaChiMapper diaChiMapper;
    private final NguoiDungMapper nguoiDungMapper;
    @NonFinal
    private final static List<String> staffRole = List.of("OUTFIT_UPDATE", "PROMOTION_UPDATE");
    @NonFinal
    private final static List<String> manager = List.of("FULL_CONTROL");
    @NonFinal
    @Value("${application.admin.email.suffix}")
    private String adminEmailsuffix;

    @Override
    @Transactional
    @ManagerRequired
    public ResponseMessage addStaff(AdminInfo info) throws MessagingException {
        Optional<NguoiDung> nguoiDung = nguoiDungRepository.findByEmail(info.email());
        if(nguoiDung.isPresent()) throw new ApplicationException(BusinessErrorCode.USER_ALREADY_EXIST);
        String rawPassword =  UIDCreator.createAdminPassword();
        String pasword = passwordEncoder.encode(rawPassword);
        String adminEmail = UIDCreator.createAdminEmail(info.email(), adminEmailsuffix);
        String id = UIDCreator.createAdminCode(info.role());
        NguoiDung admin = NguoiDung.builder()
                .khachMoi(false)
                .enabled(true)
                .id(id)
                .matKhau(pasword)
                .email(adminEmail)
                .adminEmail(info.email())
                .sdt(info.phone())
                .tenNguoiDung(info.name())
                .build();
        List<Role> role = roleRepository.findAllById(List.of("ROLE_ADMIN","ROLE_USER",info.role()));
        admin.setRoles(role.stream().collect(Collectors.toSet()));
        DiaChi diaChi = diaChiMapper.convert(info.diaChi());
        AddressDetail addressDetail = AddressDetail.builder()
                .provinceId(info.diaChi().getProvinceId())
                .districtId(info.diaChi().getDistrictId())
                .wardId(info.diaChi().getWardId())
                .diaChi(diaChi)
                .build();
        diaChi.setDetail(addressDetail);
        diaChi.setNguoiDung(admin);
        admin.setDiaChis(new HashSet<>(){{
             add(diaChi);
        }});
       NguoiDung saved = nguoiDungRepository.save(admin);
        //gửi mail
        String subject = "THÔNG TIN TÀI KHOẢN QUẢN LÝ CỦA BẠN";
        EmailTemplateEngine engine = EmailTemplateEngine.SEND_ADMIN_ACCOUNT;
        emailService.sendAdminInfo(info.email(), subject, rawPassword, info.name(), adminEmailsuffix, engine);
        return ResponseMessage.builder()
                .status(OK)
                .message("Add staff successfully")
                .build();
    }

    @Override
    @Transactional
   @HighestRequired
    public ResponseMessage deleteStaff(String id) {
        NguoiDung admin = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND, "Admin not found"));
        if (!admin.getDonThues().isEmpty())
            throw new ApplicationException(BusinessErrorCode.CANNOT_DELETE_WITH_REFERENCE, "Cannot delete admin");
        nguoiDungRepository.deleteById(id);
        return ResponseMessage.builder()
                .status(OK)
                .message("admin deleted")
                .build();
    }

    @Override
    @AdminRequired
    public ResponseMessage updateStaff(UpdateAdminInfo info) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        NguoiDung admin = nguoiDungRepository.findById(info.id())
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND, "Admin not found"));
        updawteField(info, admin);
        nguoiDungRepository.saveAndFlush(admin);
        return ResponseMessage.builder()
                .status(OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    @Transactional
    @ManagerRequired
    public ResponseMessage lockStaff(String id) {
        NguoiDung admin = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND, "Admin not found"));
        admin.setEnabled(false);
        nguoiDungRepository.save(admin);
        return ResponseMessage.builder()
                .status(OK)
                .message("Staff locked successfully")
                .build();
    }

    @Override
    @ManagerRequired
    public ResponseMessage unlockStaff(String id) {
        NguoiDung admin = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND, "Admin not found"));
        admin.setEnabled(true);
        return ResponseMessage.builder()
                .status(OK)
                .message("Staff unlocked successfully")
                .build();
    }

    @Override
    @Transactional
    @ManagerRequired
    public ResponseMessage getAllStaff(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> role = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        List<NguoiDung> admins = new ArrayList<>();
        if (role.contains("FULL_CONTROL")) {
            admins.addAll(nguoiDungRepository.findAdminsByRole(staffRole, pageable));
        } else if (role.contains(HIGHEST_ROLE.getRole())) {
            List<String> findRoles = new ArrayList<String>(){{
                addAll(staffRole);
                addAll(manager);
            }};
            admins.addAll(nguoiDungRepository.findAdminsByRole(findRoles, pageable));
        } else {
            throw new ApplicationException(BusinessErrorCode.NOT_ALLOW_DATA_SOURCE, "Unauthorized to view staffs");
        }
        List<AdminStaffDTO> staffDTOs = admins.stream().map(nguoiDungMapper::convertAdminDTO).toList();
        return ResponseMessage.builder()
                .status(OK)
                .message("Get admins successfully")
                .data(new HashMap<>() {{
                    put("staffs", staffDTOs);
                }})
                .build();
    }

    @Override
    @AdminRequired
    @Transactional
    public ResponseMessage getStaff(String id) {
        NguoiDung admin = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND, "Admin not found"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        if (admin.getEmail().equals(email)) {
            return ResponseMessage.builder()
                    .status(OK)
                    .message("Get admin successfully")
                    .data(new HashMap<>() {{
                        put("admin", nguoiDungMapper. convertAdminStaffDetailDTO(admin));
                    }})
                    .build();
        }
        ResponseMessage response = ResponseMessage.builder().status(OK).build();
        // role của người đang muốn xem
        List<String> role = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        // role của người được xem
        List<String> adminRole = admin.getRoles().stream().map(Role::getRole).toList();
        // Nếu người đang muốn xem là nhân viên quèn --> throw exception
        if (!staffRole.stream().filter(role::contains).findFirst().orElseGet(() -> "").isEmpty())
            throw new ApplicationException(BusinessErrorCode.UNAUTHORIZED_FOR_READ);
        // Nếu người được xem là SUPER_ACCOUNT --> throw exception
        if (adminRole.contains(HIGHEST_ROLE.getRole()))
            throw new ApplicationException(BusinessErrorCode.UNAUTHORIZED_FOR_READ);
        //Kiểm tra xem người được xem có quyền gì
        if (role.contains(HIGHEST_ROLE.getRole()) || !staffRole.stream().filter(adminRole::contains).findFirst().orElseGet(() -> "").isEmpty()) {
            response.setMessage("Get admin successfully");
            response.setData(new HashMap<>() {{
                put("admin", nguoiDungMapper. convertAdminStaffDetailDTO(admin));
            }});
            // Nếu là người quản lý
        } else if (!manager.stream().filter(adminRole::contains).findFirst().orElseGet(() -> "").isEmpty()) {
            throw new ApplicationException(BusinessErrorCode.UNAUTHORIZED_FOR_READ);
        } else {
            throw new ApplicationException(BusinessErrorCode.ROLE_NOT_AVAILABLE);
        }
        return response;
    }

    @Override
    @Transactional
    @ManagerRequired
    public ResponseMessage changeRole(String id, String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> authRoles = auth.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();
        NguoiDung admin = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.USER_NOT_FOUND, "Admin not found"));
        List<String> adminRoles = admin.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        //Không cho cập nhật những người có cùng quyền với nhau (ví dụ : FULL_CONTROL <--> FULL_CONTROL )
        if(new HashSet<>(authRoles).containsAll(adminRoles)) {
            throw new ApplicationException(BusinessErrorCode.UNAUTHORIZED_FOR_UPDATE);
        }
        //Không cho phép cập nhật cho người quản lý cao nhất
        if (adminRoles.contains(HIGHEST_ROLE.getRole()))
            throw new ApplicationException(BusinessErrorCode.UNAUTHORIZED_FOR_UPDATE);
        Role roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(BusinessErrorCode.ROLE_NOT_AVAILABLE));
        //Không cho phép cập nhật quyền cao nhất cho nhân viên khác (chỉ có 1 người cao nhất)
        if(roleEntity.getRole().equals(HIGHEST_ROLE.getRole())) {
            throw new ApplicationException(BusinessErrorCode.UNAUTHORIZED_FOR_UPDATE);
        }
        // Nếu người đang đươ thêm quyền đã có quyền đó rồi
        if (adminRoles.contains(role))
            throw new ApplicationException(BusinessErrorCode.DUPLICATE_DATA, "Role already exists");
        //Kiểm tra xem trong quyền của nhân viện được cập nhật có quyền nào có mức ưu tiên như vậy không
        Optional<Role> roleSamePriority = admin.getRoles().stream()
                .filter(r-> Objects.equals(r.getPriority(), roleEntity.getPriority()))
                .findFirst();
        //Nếu có thì xóa quyền ưu tiên đó
        roleSamePriority.ifPresent(value -> admin.getRoles().remove(value));
        admin.getRoles().add(roleEntity);
        nguoiDungRepository.save(admin);
        return ResponseMessage.builder()
                .status(OK)
                .message("Change role successfully")
                .build();
    }

    @Override
    @ManagerRequired
    @Transactional
    public ResponseMessage getAllRoles() {
        ResponseMessage response = ResponseMessage.builder()
                .status(OK)
                .message("Get roles successfully")
                .build();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();
        if(roles.contains(HIGHEST_ROLE.getRole())) {
            List<String> findRoles = new ArrayList<String>(){{
                addAll(manager);
                addAll(staffRole);
            }};
            response.setData(new HashMap<>(){{
                put("roles", findRoles);
            }});
        } else {
            response.setData(new HashMap<>(){{
                put("roles", staffRole);
            }});
        }
            return response;
    }

    private void updawteField(UpdateAdminInfo info, NguoiDung admin) {
        admin.setTenNguoiDung(info.tenNguoiDung());
        admin.setSdt(info.sdt());
        admin.setAdminEmail(info.email());
    }
}