package com.alpha.lainovo.utilities.token;

import com.alpha.lainovo.utilities.customUserDetails.CustomAdminDetails;
import com.alpha.lainovo.utilities.customUserDetails.CustomUserDetails;
import com.alpha.lainovo.utilities.key.ReadKeys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class GenerateToken {
    @Value("${jwt.expiration}")
    private int JWT_EXPIRATION;

    @Value("${jwt.refreshtoken.expiration}")
    private long JWT_REFRESH_EXPIRATION;

    public String generateAccessToken(CustomUserDetails customUserDetails){
        try {
            Date now = new Date();
            Date dateExpiration = new Date(now.getTime() + JWT_EXPIRATION);

            return Jwts.builder()
                    .setHeaderParam("typ","JWT")
                    .setHeaderParam("alg","RS256")
                    .claim("role",customUserDetails.getAuthorities())
                    .claim("customerId",customUserDetails.getCustomerId())
                    .claim("customerName", customUserDetails.getCustomerName())
                    .setIssuedAt(now)
                    .setExpiration(dateExpiration)
                    .signWith(SignatureAlgorithm.RS256, ReadKeys.PRIVATE_KEY)
                    .compact();
        }catch (Exception e){
            log.error("GenerateToken: generateAccessToken | Error: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public String generateRefreshToken(CustomUserDetails customUserDetails) {
        try {
            Date now = new Date();
            Date dateExpiration = new Date(now.getTime() + JWT_REFRESH_EXPIRATION);

            return Jwts.builder()
                    .setHeaderParam("typ","JWT")
                    .setHeaderParam("alg","RS256")
                    .claim("role",customUserDetails.getAuthorities())
                    .claim("customerId",customUserDetails.getCustomerId())
                    .setIssuedAt(now)
                    .setExpiration(dateExpiration)
                    .signWith(SignatureAlgorithm.RS256,ReadKeys.PRIVATE_KEY)
                    .compact();
        }catch (Exception e){
            log.error("RefresToken Fail");
            e.printStackTrace();
            return null;
        }
    }

    public String generateRefreshTokenForAdmin(CustomAdminDetails customAdminDetails) {
        try {
            Date now = new Date();
            Date dateExpiration = new Date(now.getTime() + JWT_REFRESH_EXPIRATION);
            log.info("Generate refresh token success for admin");
            return Jwts.builder()
                    .setHeaderParam("typ","JWT")
                    .setHeaderParam("alg","RS256")
                    .claim("role",customAdminDetails.getAuthorities())
                    .claim("adminId",customAdminDetails.getAdminId())
                    .setIssuedAt(now)
                    .setExpiration(dateExpiration)
                    .signWith(SignatureAlgorithm.RS256,ReadKeys.PRIVATE_KEY)
                    .compact();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String generateAccessTokenForAdmin(CustomAdminDetails customAdminDetails){
        try {
            Date now = new Date();
            Date dateExpiration = new Date(now.getTime() + JWT_EXPIRATION);

            return Jwts.builder()
                    .setHeaderParam("typ","JWT")
                    .setHeaderParam("alg","RS256")
                    .claim("role",customAdminDetails.getAuthorities())
                    .claim("adminId",customAdminDetails.getAdminId())
                    .setIssuedAt(now)
                    .setExpiration(dateExpiration)
                    .signWith(SignatureAlgorithm.RS256, ReadKeys.PRIVATE_KEY)
                    .compact();
        }catch (Exception e){
            log.error("GenerateToken: generateAccessToken | Error: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
