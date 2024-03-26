package com.alpha.lainovo.service;

import com.alpha.lainovo.utilities.customUserDetails.GetCustomerInfo;
import com.alpha.lainovo.utilities.jwt.JwtAuthenticationFilter;
import com.alpha.lainovo.utilities.token.ValidateToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetCustomerIdByRequestService {
    private final ValidateToken validateToken;
    private final GetCustomerInfo getCustomerInfo;
    public Integer getCustomerIdByRequest(HttpServletRequest request){
        try {
            String jwt = JwtAuthenticationFilter.getAccessTokenFromRequestAuthorization(request);
            if (StringUtils.hasText(jwt) && validateToken.validateToken(jwt)) {
                return getCustomerInfo.getCustomerId(jwt);
            }
        }catch (Exception e){
            log.error("-------> GetCustomerIdByRequest: error fetching customerId from JWT token in request: {}",e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }
}
