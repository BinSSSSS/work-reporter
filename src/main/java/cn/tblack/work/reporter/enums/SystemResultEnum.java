package cn.tblack.work.reporter.enums;

/**
 * 返回前端错误码
 * 
 * @author yanyu
 **/
public enum SystemResultEnum {

	/** 成功 **/
	SECCESS("成功", 0),
	/** 没有登录 **/
	NO_LOGIN("没有登录", 402),
	/** 数据库异常 **/
	DATABASE_ERROR("数据库异常", 10001),
	/** 服务器异常 **/
	SERVER_ERRPR("服务器异常", 500),
	/** 没有权限 **/
	PERMISSION("没有权限", 403),
	/** 未知错误 **/
	UNKNOW_ERROR("未知错误", -1);

	private SystemResultEnum(String desc, int code) {
		this.desc = desc;
		this.code = code;
	}

	private String desc;
	private int code;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
