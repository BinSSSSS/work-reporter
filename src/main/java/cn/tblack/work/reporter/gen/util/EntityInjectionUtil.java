package cn.tblack.work.reporter.gen.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @!_!用于对实体类进行属性的注入工具，通过传递一个Map对象来对一个对象进行属性的注入
 * @author TD唐登
 * @Date:2019年11月29日
 * @Version: 1.0(测试版)
 */
public class EntityInjectionUtil {

	/**
	 * @~传递一个任意类型的对象， 之后依靠 BeanUtils的方法将map内的属性注入到该指定对象中去。
	 * @param bean
	 * @param map
	 * @return 如果注入成功，那么返回true，否则返回false
	 */
	public static <T extends Object> boolean beanInjection(T bean, Map<String, ? extends Object> map) {

		try {
			BeanUtils.populate(bean, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	/**
	 * @!_!通过传递一个需要注入的类型， 和一个存放着注入信息的List链表进行数据的注入.
	 * @param src 用来提供作为注入类型
	 * @param mapList 用来对注入类型进行属性的注入
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> List<T> beanListInjection(T src,List<Map<String,Object>> mapList){
		
		List<T> beanList = new ArrayList<>();
		
		for (Map<String, Object> map : mapList) {
			
			try {
				T bean = (T) src.getClass().getDeclaredConstructor().newInstance();
				BeanUtils.populate(bean, map);
				beanList.add(bean);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return beanList;
	}
}
