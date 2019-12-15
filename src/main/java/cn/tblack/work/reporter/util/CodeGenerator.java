package cn.tblack.work.reporter.util;

import java.util.Random;

/**
 * @随机验证码生成器。 
 * @author TD唐登
 * @Date:2019年10月31日
 * @Version: 1.0(测试版)
 */
public class CodeGenerator {

	private static Random rand = new Random();
	
	/**
	 * @生成一个四位的数字验证码
	 * @return
	 */
	public static String createFourNumberVerficationCode() {

		/* @ 产生 1000 - 9999 之间的随机数 */
		int code = (int) Math.abs(rand.nextDouble() * 9000  + 1000);

		return "" +  code;
	}

	/**
	 * @生成一个6位的数字验证码
	 * @return
	 */
	public static String createSixNumberVerficationCode() {

		/* @ 产生 100000 - 999999 之间的随机数 */
		int code = (int) Math.abs(rand.nextDouble() * 900000  + 100000);

		return "" +  code;
	}
}
