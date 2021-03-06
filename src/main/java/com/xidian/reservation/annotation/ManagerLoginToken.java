package com.xidian.reservation.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：Maolin
 * @className ：UserLoginToken
 * @date ：Created in 2019/9/7 20:10
 * @description： 管理员身份验证，需要登录才能进行操作的注解
 * @version: 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerLoginToken {
    boolean required() default true;
}
