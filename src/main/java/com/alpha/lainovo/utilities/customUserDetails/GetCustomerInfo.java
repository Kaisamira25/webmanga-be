package com.alpha.lainovo.utilities.customUserDetails;


import com.alpha.lainovo.utilities.key.ReadKeys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetCustomerInfo {
    public Integer getCustomerId(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(ReadKeys.PUBLIC_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("customerId", Integer.class);
        }catch (Exception e) {
            log.error("GetCustomerInfo: getCustomerId | error: {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public Integer getAdminId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(ReadKeys.PUBLIC_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("adminId", Integer.class);
        } catch (Exception e) {
            log.error("GetAdminInfo: getCustomerId | error: {}", e.getMessage());
        }
        return null;
    }
}
