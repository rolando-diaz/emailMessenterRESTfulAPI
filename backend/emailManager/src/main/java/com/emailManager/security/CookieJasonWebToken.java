package com.emailManager.security;


import com.emailManager.enums.SUCCESS;
import com.emailManager.entity.RemoteHost;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;

@Service
public class CookieJasonWebToken {
    private static final long serialversionUID = 121345637L;
    private final String JWT_HEADER = SUCCESS.TOKEN_HEADER.toString();
    private final String KEY = SUCCESS.TOKEN_KEY.toString();

    /**
     * @param remoteHost NOT NULL
     * @return a cookie with a Jwt token embedded
     */
    public Cookie creatCookieJwt(RemoteHost remoteHost){
        String jwt = creatJwtToken(remoteHost);
        return new Cookie(JWT_HEADER, jwt);

    }

    /**
     * @param remoteHost NOT NULL
     * @return a JwtToken string with a remoteHost obj embedded.
     */
    public String creatJwtToken(RemoteHost remoteHost){
        String Jwt = Jwts.builder().claim(JWT_HEADER, remoteHost).signWith(SignatureAlgorithm.HS256, KEY)
                .setIssuedAt(new Date(Instant.now().toEpochMilli())).compact();
        return Jwt;
    }

    /**
     * @param jwts
     * @return the RemoteHost obj contained in the jwt string
     */
    public RemoteHost getJwtRemoteHost(String jwts){
        try{
            Object token = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwts).getBody().get(JWT_HEADER);

           return (new ObjectMapper()).convertValue(token, RemoteHost.class);
        }catch (Exception e){}
        return null;
    }

    /**
     * @param request
     * @return the RemoteHost obj contained in the Cookie (Http request param)
     */
    public RemoteHost getCookieRemoteHost(HttpServletRequest request){
        Cookie cookies[] = request.getCookies();
        String jwts, cookieName;
        RemoteHost remoteHost;
        if(cookies != null){
            for(Cookie c: cookies){
                cookieName = c.getName();
                jwts = c.getValue();
                if(cookieName !=null && cookieName.equals(JWT_HEADER)) {
                    remoteHost = getJwtRemoteHost(jwts);
                    if(remoteHost != null)
                        return remoteHost;
                }
            }
        }
        return null;
    }

    /**
     * @return the JWT header string
     */
    public String getJWT_HEADER() {
        return JWT_HEADER;
    }
}
