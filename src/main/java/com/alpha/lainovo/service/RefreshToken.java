package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Customer;
import com.alpha.lainovo.service.ServiceInterface.CreateAndUpdateInterface;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import com.alpha.lainovo.utilities.customUserDetails.CustomUserDetails;
import com.alpha.lainovo.utilities.jwt.JwtAuthenticationFilter;
import com.alpha.lainovo.utilities.token.GenerateToken;
import com.alpha.lainovo.utilities.token.ValidateToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshToken {
    private final ValidateToken validateToken;

    private final CustomerInterface customerInterface;

    private final CreateAndUpdateInterface<Integer, Customer> update;

    private final GenerateToken generateToken;

    public String generateAccessToken(HttpServletRequest request){
        String token = null;
        String jwt = JwtAuthenticationFilter.getRefreshTokenFromCookie(request);

        Customer customer = customerInterface.findByRefreshToken(jwt);

        if (customer != null){
            if (!validateToken.validateToken(jwt)) {
                customer.setRefreshToken(null);
                update.update(customer.getCustomerId(), customer);
                log.error("RefreshToken: generateToken | Refresh Token expired");
                return "1";
            }

            CustomUserDetails customUserDetails = new CustomUserDetails(customer.getCustomerId(),
                    customer.getEmail(),
                    customer.getPassword(),
                    true,
                    Arrays.stream(customer.getRole().name().split(",")).
                            map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            token = generateToken.generateAccessToken(customUserDetails);
            log.info("-------> RefreshToken: generateAccessToken | New accessToken {}",token);
            return token;
        }
        return "0";
    }
}
