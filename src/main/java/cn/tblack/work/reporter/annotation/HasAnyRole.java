package cn.tblack.work.reporter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @=-=该注解用于权限验证-表示某个请求需要登录并具有在数据库内分配的角色才能够访问
 * @=-=该注解内封装了@PreAuthorize注解，并使用该注解来具体的完成封装
 * @author TD唐登
 * @Date:2019年11月22日
 * @Version: 1.0(测试版)
 */
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@java.lang.annotation.Inherited
@java.lang.annotation.Documented
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public @interface HasAnyRole {

}
