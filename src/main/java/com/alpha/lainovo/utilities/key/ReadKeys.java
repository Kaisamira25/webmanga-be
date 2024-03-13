package com.alpha.lainovo.utilities.key;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
@Slf4j
public class ReadKeys {
    public static final String FILE_PRIVATE_KEY = "privatekey.der";

    public static final String FILE_PUBLIC_KEY = "publickey.pem";

    public static RSAPrivateKey PRIVATE_KEY;

    public static PublicKey PUBLIC_KEY;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void getPrivateKey(){
        try {
            Resource resource = resourceLoader.getResource("classpath:" + FILE_PRIVATE_KEY);

            byte[] keyByte = null;
            if (resource.exists()){
                try (InputStream inputStream = resource.getInputStream()){
                    keyByte = new byte[inputStream.available()];
                    inputStream.read(keyByte);
                }
            }else {
                log.error("Cannot read Private key");
            }
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyByte);
            PRIVATE_KEY = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void getPublicKey(){
        try {
            Resource resource = resourceLoader.getResource("classpath:" + FILE_PUBLIC_KEY);
            byte[] keyByte = null;
            if (resource.exists()){
                try (InputStream inputStream = resource.getInputStream()){
                    keyByte = new byte[inputStream.available()];
                    inputStream.read(keyByte);
                }
            }else {
                log.error("Cannot read public key");
            }
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyByte);
            PUBLIC_KEY = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
        }catch (Exception e){
            log.error("Cannot read public key");
            e.printStackTrace();
        }
    }
}
