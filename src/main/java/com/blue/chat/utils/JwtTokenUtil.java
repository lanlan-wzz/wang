package com.blue.chat.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.blue.chat.manager.UserHolder;

/**
 * @author 26020
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    private static final String AUTHORITIES="authorities";
    private static final String USER_ID="uid";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /**
     * 根据用户信息生成Token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        HashMap<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        claims.put(USER_ID, UserHolder.getUserWechat());
        claims.put(AUTHORITIES,userDetails.getAuthorities());
        return generateToken(claims);
    }

    /**
     * 从Token中获取登录用户名
     * @param token token
     * @return 用户名
     */

    public String getUsernameFromToken(String token){
        String username;
        try {
            Claims clams=getClaimFromToken(token);
            username=clams.getSubject();
        }catch (Exception e){
            username=null;
            e.printStackTrace();
        }
        return username;
    }
    /**
     * 从Token中获取登录用户名
     * @param token token
     * @return 用户名
     */

    public String getUserWechat(String token){
        String wechat;
        try {
            Claims clams=getClaimFromToken(token);
            wechat = clams.get(USER_ID, String.class);
        }catch (Exception e){
            wechat=null;
            e.printStackTrace();
        }
        return wechat;
    }


    /**
     * 从Token中获取荷载
     * @param token token
     * @return 荷载
     */
    public  Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 根据荷载生成Jwt
     * @param claims 荷载
     * @return token
     */

    private String generateToken(Map<String,Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generataExpirationDate())
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    /**
     * 获取过期时间
     * @return 过期时间
     */
    private Date generataExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 验证Token是否有效
     * @param token token
     * @param userDetails UserDetails
     * @return token是否有效
     */
    public boolean validateToken(String token,UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())&& !isTokenExpired(token);
    }

    /**
     * 判断Token是否失效
     * @param token token
     * @return 是否失效
     */
    private boolean isTokenExpired(String token) {

        Date expirationDate=getExpiredDateFromToken(token);
        return expirationDate.before(new Date());
    }

    /**
     * 获取失效时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断Token是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    public String refreshToken(String token){
        Claims claims = getClaimFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    public String getWechatToken(HttpServletRequest request){
        String header = request.getHeader(tokenHeader);
        String token = header.substring(tokenHead.length());
        return  getUserWechat(token);
    }
}
