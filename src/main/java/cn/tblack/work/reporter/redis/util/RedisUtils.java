package cn.tblack.work.reporter.redis.util;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private KeyGenerator cacheKeyGenerator;
	
	private static Logger log =  LoggerFactory.getLogger(RedisUtils.class);
	
	/**
	 * <span>添加缓存对象到 redis 中</span>
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putKey(final String key, Object value) {

		ValueOperations<String, Object> operations = redisTemplate.opsForValue();

		try {
			operations.set(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <span>添加缓存对象</span>
	 * @param key
	 * @param value
	 * @param expired
	 * @return
	 */
	public boolean putKey(final String key, Object value, Long expired) {
		
		ValueOperations<String,Object> operation = redisTemplate.opsForValue();
		
		try {
			operation.set(key, value);
			redisTemplate.expire(key, expired, TimeUnit.SECONDS);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean exist(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * <span>删除某个对象</span>
	 * 
	 * @param key
	 */
	public void remove(final String key) {

		if (exist(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * <span>删除多个缓存对象</span>
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {

		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * <span>根据正则表达式来删除对象</span>
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {

		Set<String> keys = redisTemplate.keys(pattern);
		
		if (keys.size() > 0) {
			log.info("删除的数据量: " + redisTemplate.delete(keys));
			
		}
	}

	/**
	 * @~_~拿到Key对应的value
	 * @param key
	 * @return
	 */
	public Object getValue(final String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * @~_~通过一个PJP对象生成一个用于在Redis缓存操作中使用的Key
	 * @param proceeding
	 * @return
	 */
	public String generatorKey(JoinPoint proceeding) {
		//首先根据当前调用的方法和参数生成一个Key值
		Object[] params = proceeding.getArgs();
		Object target = proceeding.getTarget();
		Method method = null;
		Signature signature = proceeding.getSignature();
		
		if(signature instanceof MethodSignature) {
			method = ((MethodSignature)signature).getMethod();
		}
		return  cacheKeyGenerator.generate(target, method, params).toString();
	}
}
