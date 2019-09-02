package com.xidian.reservation.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xidian.reservation.annotation.PassToken;
import com.xidian.reservation.annotation.UserLoginToken;
import com.xidian.reservation.service.ConsumerService;
import com.xidian.reservation.service.ManagerService;
import com.xidian.reservation.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author ：Maolin
 * @className ：AuthenticationInterceptor
 * @date ：Created in 2019/9/2 20:13
 * @description： 拦截配置
 * @version: 1.0
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String SECRET = "JKKLJOoasdlfjgkGYD";

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ConsumerService consumerService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("【Token】Token does not exist! Please login again!");
                }
                // 获取 token 中的 user id（用户名）
                int userId;
                try {
                    userId = TokenUtil.getAppUID(token);
                    //System.out.println(userName);
                    //userName = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("【Token】401");
                }

                if (managerService.findManagerById(userId) == null && consumerService.findConsumerById(userId) == null) {
                    throw new RuntimeException("【Token】User does not exist!");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("【Token】401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
