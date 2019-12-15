package cn.tblack.work.reporter.gen.util;

public class DBNameConverterUtils {

	/**
	 * @!_!转换为Java的类名
	 * @param tableName
	 * @return
	 */
	public static String toJavaClassName(String tableName) {

		StringBuffer buffer = new StringBuffer(toCamelCase(tableName));
		String s = String.valueOf(tableName.charAt(0));
		buffer.replace(0, 1, s.toUpperCase());
		return buffer.toString();
	}
	
	/**
	 * !_!从下划线命名法转换为驼峰命名法
	 * @param src
	 * @return
	 */
	public static String toCamelCase(String src) {

		//为了防止系统的表名为全大写， 首先将表名转化为全小写
		StringBuffer buffer = new StringBuffer(src.toLowerCase());

		
		//如果该表名无需要转换
		int index =  buffer.indexOf("_");
		if (index == -1) {
			return src;
		}
		
		do {
			
			String s = String.valueOf(buffer.charAt(index + 1));
			buffer.charAt(index + 1);
			buffer.replace(index, index + 2, s.toUpperCase());
			index = buffer.indexOf("_");
		}while(index != -1);
		
		
		return buffer.toString();
	}

	public static void main(String[] args) {
		
		System.err.println(toJavaClassName("QRTZ_CLASS_table_B_a"));
	}
}
