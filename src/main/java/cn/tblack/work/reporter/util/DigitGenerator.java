package cn.tblack.work.reporter.util;



/**
 * <span>用来产生数字- 字母的工具类</span>
 * @author TD唐登
 * @Date:2019年6月13日
 * @Version: 1.0(测试版)
 */
public class DigitGenerator {

	private final static char[] DIGIT;	//存放着0-9的数字
	private final static char[] ALPHABET; //存放52个字母
	
	
	
	/*@ 静态代码块， 在类被加载的时候初始化*/
	static{
		DIGIT = new char[10];
		
		for(int i = 0;i < DIGIT.length;++i) {
			DIGIT[i] = (char) ('0' + i);
		}
		
		ALPHABET = new char[52];  
		
		for(int i  = 0; i < ALPHABET.length / 2; ++i) {
			ALPHABET[i] = (char) ('A' + i);
		}
		
		for(int i  = ALPHABET.length / 2; i < ALPHABET.length; ++i) {
			ALPHABET[i] = (char) ('a' + i -  ALPHABET.length / 2);
		}		
	}
	
	/**
	 * @ 返回包含0-9数字的字符数组
	 * @return
	 */
	public static char[] numbers() {
		return DIGIT;
	}
	
	/**
	 * @ 返回包含 a-z A-Z 的字母表
	 * @return
	 */
	public static char[] alphabet() {
		return ALPHABET;
	}
}
