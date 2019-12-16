package cn.tblack.work.reporter.oss.enums;

import cn.tblack.work.reporter.converter.AbstractEnumConverter;
import cn.tblack.work.reporter.enums.PersistEnum2DB;

/**
 * @__!_!_!用于描述数据库中的OSS存储桶类型
 * @author TD唐登
 * @Date:2019年12月9日
 * @Version: 1.0(测试版)
 */
public enum OssType  implements PersistEnum2DB<Integer>{
	
	ALIYUN(1,"阿里云存储桶"),
	QCLOUD(2,"腾讯云存储桶"),
	QINIU(3,"七牛存储桶");
	
	
	private Integer value;
	private String desc;
	
	private OssType(Integer value, String desc) {
		this.value = value;
		this.desc = desc; 
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * @_!_!通过传递一个数值类型的值来进行返回一个枚举类型
	 * @param value
	 * @return
	 */
	public static OssType valueOf(int value) {
		
		OssType[] types =  OssType.values();
		
		for (OssType ossType : types) {
			if(ossType.getValue() == value)
				return ossType;
		}
		return null;
	}
	
	/**
	 * @_!_!_用于将枚举类型转换为数值类型
	 * @author TD唐登
	 * @Date:2019年12月9日
	 * @Version: 1.0(测试版)
	 */
	public static class Converter extends AbstractEnumConverter<OssType, Integer>{
		public Converter() {
			
			super(OssType.class);
		}
	}

	@Override
	public Integer getData() {
		return value;
	}

	private OssType() {
		
	}
}
