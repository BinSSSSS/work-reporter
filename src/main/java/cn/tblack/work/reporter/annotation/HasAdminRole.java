package cn.tblack.work.reporter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @=-=该注解是对于@PreAuthorize注解的封装，表示需要具有管理员角色才可以访问
 * @author TD唐登
 * @Date:2019年11月22日
 * @Version: 1.0(测试版)
 */
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ADMIN')")
public @interface HasAdminRole {

}
