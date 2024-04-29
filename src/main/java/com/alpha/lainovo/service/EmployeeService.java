package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.EmployeeDTO;
import com.alpha.lainovo.dto.request.EmployeeLoginDTO;
import com.alpha.lainovo.model.Admin;
import com.alpha.lainovo.model.Role;
import com.alpha.lainovo.repository.RoleRepository;
import com.alpha.lainovo.service.ServiceInterface.CheckStringInterface;
import com.alpha.lainovo.service.ServiceInterface.EmployeeInterface;
import com.alpha.lainovo.utilities.customUserDetails.CustomAdminDetails;
import com.alpha.lainovo.utilities.token.GenerateToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {
    private final RoleRepository roleRepository;
    private final EmployeeInterface employeeInterface;
    @Autowired
    @Qualifier("CheckPassword")
    private final CheckStringInterface checkPasswordFormat;
    private final AuthenticationManager authenticationManager;
    private final GenerateToken generateToken;
    private final CookieService cookieService;
    private final PasswordEncoder encoder;
    @Value("${jwt.refreshtoken.expiration}")
    private long JWT_REFRESH_EXPIRATION;
    @Transactional
    public Integer createAccountEmployee(EmployeeDTO employeeDTO) {
        if (employeeInterface.findAdminAccountByAccountName(employeeDTO.accountName()) == null) {
            if (!checkPasswordFormat.isStringValid(employeeDTO.password())) {
                // Nếu password sai định dạng thì sẽ trả về false sau đó dùng ! để trả về true để thực thi đoạn code
                // return 0; bên trong
                return 0; //
            }
            Admin admin = new Admin();
            admin.setAccountName(employeeDTO.accountName());
            admin.setFullName(employeeDTO.fullName());
            admin.setPassword(encoder.encode(employeeDTO.password()));
            admin.setPhone(employeeDTO.phone());
            admin.setAddress(employeeDTO.address());
            admin.setBlocked(false);
            Role role = roleRepository.findByRoleName("EMPLOYEE");
//            List<Role> roles = new ArrayList<>();
//            roles.add(role);
//            admin.setRoles(roles);
//            // Add the admin to the role
//            List<Admin> admins = new ArrayList<>();
//            admins.add(admin);
//            role.setAdmins(admins);
            admin.setRole(role);
            employeeInterface.createEmployee(admin);
            log.info("Create employee success: {}", admin.getAccountName());
            return 1; // Tạo thành công
        }
        return 2; // Tạo thất bại
    }
    public Object loginAccountAdmin(EmployeeLoginDTO employeeLoginDTO) {
       log.info("Login account admin -----> Start");
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(employeeLoginDTO.accountName(), employeeLoginDTO.password()));
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomAdminDetails customAdminDetails = (CustomAdminDetails) authentication.getPrincipal();
            Admin admin = employeeInterface.findAdminAccountByAccountName(employeeLoginDTO.accountName());
            log.info("Find admin account: {}",admin);
            customAdminDetails.setAdminId(admin.getAdminId());
            admin.setRefreshToken(generateToken.generateRefreshTokenForAdmin(customAdminDetails));
            employeeInterface.updateEmployee(admin.getAdminId(),admin);
            String jwt = generateToken.generateAccessTokenForAdmin(customAdminDetails);
            Map<String, String> list = new HashMap<>();
            list.put("accessToken",jwt);
            list.put("refreshToken", admin.getRefreshToken());

            cookieService.add("refreshTokenAdmin",admin.getRefreshToken(), (int) JWT_REFRESH_EXPIRATION);
            log.info("-----> Login success");
            return list;
        }
        log.info("Login fail");
        return null;
    }

    public Integer validate(EmployeeLoginDTO employeeLoginDTO) {
        Admin admin = employeeInterface.findAdminAccountByAccountName(employeeLoginDTO.accountName());
        log.info("Admin {}", admin);
        if (admin == null) {
            log.info("Login: Account doesn't exist");
            return 0;
        } else if (encoder.matches(employeeLoginDTO.password(), admin.getPassword()) && !admin.isBlocked()) {
            log.info("Login: Login success: {}",admin.getAccountName());
            return 1;
        } else if (admin.isBlocked()) {
            log.info("Login: Account blocked");
            return 2;
        }
        log.info("Login: Exception: {}", admin.getAccountName());
        return 3;
    }
}
