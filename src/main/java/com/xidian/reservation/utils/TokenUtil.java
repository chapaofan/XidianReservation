package com.xidian.reservation.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.xidian.reservation.exceptionHandler.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Maolin
 * @className ：TokenUtil
 * @date ：Created in 2019/9/2 20:32
 * @description： Token 工具类
 * @version: 1.0
 */
@Slf4j
public class TokenUtil {

    /**
     * APP登录Token的生成和解析
     */

    /**
     * token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfjgkGYD
     * 必须和AuthenticationInterceptor中一致
     */
    private static final String SECRET = "JKKLJOoasdlfjgkGYD";
    /**
     * token 过期时间: 1天
     */
    private static final int calendarField = Calendar.DATE;

    private static final int calendarInterval = 1;

    /**
     * JWT生成Token.
     * <p>
     * JWT构成: header, payload, signature
     *
     * @param user_id 登录成功后用户user_id, 参数user_id不可传空
     */

    public static String getToken(String user_id) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = "";
        token = JWT.create()
                .withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP")
                .withClaim("user_id",user_id)
                //.withAudience(user_id)
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws RuntimeException
     */
    private static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            throw new BizException("600","Token is illegal or expired");
            //throw new RuntimeException("Token is illegal or expired");
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static String getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("user_id");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            throw new BizException("600","Token is illegal or expired");
            //throw new RuntimeException("Token is illegal or expired");
        }
        return user_id_claim.asString();
    }
}