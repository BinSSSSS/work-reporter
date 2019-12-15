package cn.tblack.work.reporter.enums;

/**
 * 星期类枚举
 * 
 * @author yanyu
 **/
public enum WeekEnum {
	/** 星期一 **/
	MONDAY(1, "星期一"),
	/** 星期二 **/
	TUESDAY(2, "星期二"),
	/** 星期三 **/
	WEDNESDAY(3, "星期三"),
	/** 星期四 **/
	THURSDAY(4, "星期四"),
	/** 星期五 **/
	FRIDAY(5, "星期五"),
	/** 星期六 **/
	SATURDAY(6, "星期六"),
	/** 星期天 ***/
	SUNDAY(7, "星期天");

	private int value;
	private String name;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private WeekEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

}
