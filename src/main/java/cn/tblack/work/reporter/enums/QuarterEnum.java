package cn.tblack.work.reporter.enums;
/** 季度枚举 
 * @author yanyu
 * ***/
public enum QuarterEnum {

	/** 第一季度 **/
	FIRST_QUARTER("第一季度", 1),
	/** 第二季度 **/
	SECOND_QUARTER("第二季度", 2),
	/** 第三季度 **/
	THIRD_QUARTER("第三季度", 3),
	/** 第四季度 **/
	FOURTH_QUARTER("第四季度", 4);
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

	private QuarterEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

}
