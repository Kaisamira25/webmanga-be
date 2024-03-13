package com.alpha.lainovo.utilities.customUserDetails;


import com.alpha.lainovo.utilities.key.ReadKeys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetUserInfo {
    public Integer getUserId(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(ReadKeys.PRIVATE_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            log.info("GetUserInfo: getUserId");
            return claims.get("userId", Integer.class);
        }catch (Exception e) {
            log.error("GetUserIno: getUserId | error: {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
