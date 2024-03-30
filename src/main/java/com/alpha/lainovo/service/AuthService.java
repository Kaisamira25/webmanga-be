package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.LoginDTO;
import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.service.ServiceInterface.CreateAndUpdateInterface;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import com.alpha.lainovo.utilities.customUserDetails.CustomUserDetails;
import com.alpha.lainovo.utilities.token.GenerateToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final GenerateToken generateToken;

    private final CustomerInterface customerInterface;

    private final CreateAndUpdateInterface<Integer, Customer> update;

    private final PasswordEncoder encoder;

    private final GetCustomerIdByRequestService getCustomerIdByRequestService;

    @Value("${jwt.refreshtoken.expiration}")
    private long JWT_REFRESH_EXPIRATION;

    private final CookieService cookieService;

    public Object login(LoginDTO loginDTO, HttpServletRequest request){
        log.info("------> Login start working");

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
        if (authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Customer customer = customerInterface.findByEmail(customUserDetails.getEmail());
            customUserDetails.setUserId(customer.getCustomerId());
            customer.setRefreshToken(generateToken.generateRefreshToken(customUserDetails));
            update.update(customer.getCustomerId(), customer);
            String jwt = generateToken.generateAccessToken(customUserDetails);
            Map<String, String> list = new HashMap<>();
            list.put("accessToken", jwt);
            list.put("refreshToken", customer.getRefreshToken());

            cookieService.add("refresh-token", customer.getRefreshToken(), (int) JWT_REFRESH_EXPIRATION);
            log.info("------> Login success");
            return list;
        }
        log.info("Login null");
        return null;
    }

    public Integer validate(LoginDTO loginDTO){
        Customer customer = customerInterface.findByEmail(loginDTO.email());
        if (customer == null) {
            log.info("------> Login: User does not exist");
            return 0;
        } else if (encoder.matches(loginDTO.password(), customer.getPassword()) && customer.getIsVerify()){
            log.info("------> Login successful with email: {}",loginDTO.email());
            return 1;
        }else if (!customer.getIsVerify()){
            log.info("------> Login fail because this email verify == false");
            return 2;
        }else if (customer.getIsBlocked()){
            log.info("------> Login fail because this account has been locked");
            return 3;
        }
        log.info("------> Login: exceptions: {}", customer.getEmail());
        return 4;
    }
    public void logout(HttpServletRequest request){
        Integer userId = getCustomerIdByRequestService.getCustomerIdByRequest(request);
        Customer customer = customerInterface.findById(userId);
        customer.setRefreshToken("");
        update.update(userId, customer);
    }
}
