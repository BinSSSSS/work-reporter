package cn.tblack.work.reporter.util;

/**
 * <span>用于对传入的字符进行MD5加密</span>
 * @author TD唐登
 * @Date:2019年6月13日
 * @Version: 1.0(测试版)
 */
public class MD5Utils {

	private static String KEY = "TD@998.cn.tblack"; // 加密需要使用到的密钥
	private static int OFFSET = 4; // 加密的偏移量
//	private static char[] DIRCTIONAY = "0123456789abcdefABCDEF".toCharArray(); // 加密字典，保存着16进制的数字
	private static char[] DIRCTIONAY;
	private static int CIPHER_LEN = 32;

	public static String encrypt(String data) {

		data += KEY;
		
		return encryptByOffset(data.getBytes());
	}
	static {
		DIRCTIONAY = new char[DigitGenerator.alphabet().length + DigitGenerator.numbers().length];
		int i = 0;
		for(char c : DigitGenerator.numbers()) {
			DIRCTIONAY[i++] = c;
		}
		for(char c : DigitGenerator.alphabet()) {
			DIRCTIONAY[i++] = c;
		}
	}
	
	/**
	 * @ 自定义的加密算法， 通过使用和密钥结合和偏移来生成一个密钥。
	 * 
	 * @param data
	 * @return
	 */
	private static String encryptByOffset( byte[] bytes) {

		char[] cipherText = new char[bytes.length * 2];
		int cnt = 0;

		for (int i = 0; i < bytes.length; ++i) {
			cipherText[cnt++] = DIRCTIONAY[(bytes[i] >> OFFSET & DIRCTIONAY.length)];
			cipherText[cnt++] = DIRCTIONAY[(bytes[i]  & DIRCTIONAY.length)];
		}

		return new String(cipherText, 0, CIPHER_LEN);
	}
	public static void main(String[] args) {
		System.err.println(encrypt("bins1234"));

		
	}
}
