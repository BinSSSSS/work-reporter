package cn.tblack.work.reporter.util;

import java.util.Date;

/**
 * @^=^一些用于时间相关的方法
 * @author TD唐登
 * @Date:2019年11月25日
 * @Version: 1.0(测试版)
 */
public class DateUtils {

	/**
	 * @ =^-^=传递一个时间来检测该时间相对于当前时间是否已经失效
	 * @return
	 */
	public static boolean isExpired(Date date) {

		return date.getTime() > new Date().getTime();
	}

	/**
	 * @=-=*=传递两个时间来进行比较， 比较前一个时间是否相对于第二个时间已经过期
	 */
	public static boolean isExpired(Date before, Date after) {

		return before.getTime() > after.getTime();
	}
}
