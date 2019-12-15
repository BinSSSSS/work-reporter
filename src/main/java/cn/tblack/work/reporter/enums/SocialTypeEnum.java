package cn.tblack.work.reporter.enums;
/**
 * 社交组建枚举
 * 
 * @author yanyu
 **/
public enum SocialTypeEnum {
	/** 腾讯 **/
	QQ("qq"),
	/** 微信 **/
	WEIXIN("weixin"),
	/*** github **/
	GITHUB("github"),
	/** 阿里 **/
	ALI("ali"),
	/** 微博 **/
	WEIBO("sina"),
	/** 百度 **/
	BAIDU("baidu"),
	/** 码云（已废弃） **/
	MAYUN("mayun");
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private SocialTypeEnum(String value) {
		this.value = value;
	}

}
