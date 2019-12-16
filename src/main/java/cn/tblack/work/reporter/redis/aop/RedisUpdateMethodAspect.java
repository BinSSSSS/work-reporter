package cn.tblack.work.reporter.redis.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.tblack.work.reporter.redis.util.RedisUtils;

/**
 * @~_~当触发更新操作的时候删除Redis缓存的切面对象
 * @author TD唐登
 * @Date:2019年12月15日
 * @Version: 1.0(测试版)
 */
@Component
@Aspect
public class RedisUpdateMethodAspect {

	@Autowired
	private RedisUtils redisUtils;
	
	@Pointcut("execution (* cn.tblack.work.reporter.*.service.*.*.update*(..)) || "
			+ "execution (* cn.tblack.work.reporter.*.service.*.*.save*(..)) || "
			+ "execution (* cn.tblack.work.reporter.*.service.*.*.delete*(..)) || "
			+ "execution (* cn.tblack.work.reporter.*.service.*.*.remove*(..)) || "
			+ "execution (* cn.tblack.work.reporter.*.service.*.*.revoke*(..)) || "
			+ "execution (* cn.tblack.work.reporter.*.service.*.*.grant*(..)) || "
			+ "execution (* cn.tblack.work.reporter.*.service.*.*.flush*(..))")
	public void pointcut() {
		
	}
	
	@Before("pointcut()")
	public void onBefore(JoinPoint joinPoint) {
		
		/*更新操作的时候，只需要删除该类相关的缓存数据---更好的办法是过滤，只删除本条数据，因为本系统数据量不大，故只简单实现*/
		/*表达式生成只需要检查类名是否一致。注意: Key的表达式与Java的正则表达式不同,使用Ant风格作为表达式*/
		String pattern = joinPoint.getTarget().getClass().getName() + "*";
		
		//移除所有该类的查询缓存。~更好的办法只移除该字段的缓存和更新查询全部的缓存。简便起见
		redisUtils.removePattern(pattern);
		
	}

}
