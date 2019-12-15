package cn.tblack.work.reporter.enums;

import cn.tblack.work.reporter.converter.AbstractEnumConverter;

public enum VCodeEmailTypes implements PersistEnum2DB<Integer> {

	/** 用于注册验证的验证码 */
	REGISTER_CODE(1,"注册验证码"),
	/** 用于修改密码使用的验证码 */
	PASSWORD_RESET_CODE(2,"忘记密码验证码"),
	/** 用于忘记密码使用的验证码 */
	PASSWORD_FORGET_CODE(3,"重置密码验证码"),
	/** 用于安全验证使用的验证码--如长时间未登录-异地登录等 */
	SECURITY_VERIFY_CODE(4,"安全验证验证码"),
	/** 用于消费使用的验证码 */
	PAY_CODE(5,"消费验证码");

	int type;
	String value;

	private VCodeEmailTypes(int type,String value) {
		this.type = type;
		this.value =  value;
	}

	public int getType() {
		return type;
	}
	public String getValue() {
		return value;
	}

	public static VCodeEmailTypes valueOf(int type) {

		VCodeEmailTypes[] types = VCodeEmailTypes.values();

		for (VCodeEmailTypes vCodeEmailTypes : types) {
			if (vCodeEmailTypes.getType() == type) {
				return vCodeEmailTypes;
			}
		}
		return null;
	}

	@Override
	public Integer getData() {
		return type;
	}
	
	/**
	 * @&*&用于进行数据库类型转换
	 * @author TD唐登
	 * @Date:2019年11月25日
	 * @Version: 1.0(测试版)
	 */
	public static class Converter extends AbstractEnumConverter<VCodeEmailTypes, Integer> {

		public Converter() {
			
			super(VCodeEmailTypes.class);
		}
	}
	private VCodeEmailTypes() {
	}
}
