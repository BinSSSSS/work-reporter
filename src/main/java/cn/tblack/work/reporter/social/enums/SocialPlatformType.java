package cn.tblack.work.reporter.social.enums;

import cn.tblack.work.reporter.converter.AbstractEnumConverter;
import cn.tblack.work.reporter.enums.PersistEnum2DB;

/**
 * @~_~第三方社交平台登录类型
 * @author TD唐登
 * @Date:2019年12月13日
 * @Version: 1.0(测试版)
 */
public enum SocialPlatformType implements PersistEnum2DB<Integer>{

	QQ(1),
	WECHAT(2),
	WEIBO(3),
	BAIDU(4),
	GITHUB(5);
	
	
	private Integer value;
	
	
	private SocialPlatformType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getData() {
		return value;
	}
	
	public static SocialPlatformType valueOf(int value) {
		
		SocialPlatformType[] values = values(); 
		
		for (SocialPlatformType socialPlatformType : values) {
			if(socialPlatformType.getValue() == value)	
				return socialPlatformType;
		}
		
		return null;
	}
	
	public static class Converter extends AbstractEnumConverter<SocialPlatformType, Integer>{

		public Converter() {
			super(SocialPlatformType.class);
		}
		
	}
	
	private SocialPlatformType() {
		
	}
}
