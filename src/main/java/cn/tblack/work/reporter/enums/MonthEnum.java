package cn.tblack.work.reporter.enums;

/**
 * 月份枚举
 * 
 * @author yanyu
 *
 */
public enum MonthEnum {
	/** 一月份 **/
	JANUARY("一月份", 1),
	/** 二月份 **/
	FEBRUARY("二月份", 2),
	/** 三月份 **/
	MARCH("三月份", 3),
	/** 四月份 **/
	APRIL("四月份", 4),
	/** 五月份 **/
	MAY("五月份", 5),
	/** 六月份 **/
	JUNE("六月份", 6),
	/** 七月份 **/
	JULY("七月份", 7),
	/** 八月份 **/
	AUGUST("八月份", 8),
	/** 九月份 **/
	SEPTEMBER("九月份", 9),
	/** 十月份 **/
	OCTOBER("十月份", 10),
	/** 十一月份 **/
	NOVEMBER("十一月份", 11),
	/** 十二月份 **/
	DECEMBER("十二月份", 12);
	String name;
	int value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private MonthEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

}
