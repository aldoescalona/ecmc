/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.app.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Singleton;
import org.apache.log4j.Logger;

/**
 *
 * @author Aldo Escalona
 */
@Singleton
public class JWTKey {

    private Key key;
    private static Logger logger = Logger.getLogger(JWTKey.class);

    @PostConstruct
    public void init() {
        try {
            byte[] keyv = "KJDIUU#%YYU&$%#%?Ñ;:".getBytes(); 
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            keyv = sha.digest(keyv);
            keyv = Arrays.copyOf(keyv, 16);
            key = new SecretKeySpec(keyv, "AES");
            
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    public Key getKey() {
        return key;
    }

    public String token(Object id, String username, String roles, Integer horas) {

        Date issueDate = new Date();
        JwtBuilder builder = Jwts.builder();

        if (roles != null) {
            builder.claim("roles", roles);
        }

        if (horas != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(issueDate);
            calendar.add(Calendar.HOUR, horas);
            Date expireDate = calendar.getTime();
            builder.setExpiration(expireDate);
        }

        builder.setId(id.toString())
                .setSubject(username)
                .setIssuer("http://www.chilaquilesaldama.mx")
                .setIssuedAt(issueDate)
                .signWith(SignatureAlgorithm.HS512, key).claim("id", id);

        return builder.compact();
    }

}
