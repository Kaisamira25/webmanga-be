package com.alpha.lainovo.utilities.token;

import com.alpha.lainovo.utilities.key.ReadKeys;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidateToken {
    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(ReadKeys.PUBLIC_KEY)
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            log.error("ValidateToken: validateToken | error: {}", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
