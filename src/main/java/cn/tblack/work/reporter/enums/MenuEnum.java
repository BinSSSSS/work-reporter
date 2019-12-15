package cn.tblack.work.reporter.enums;

/**
 * URL菜单类型
 * 
 * @author yanyu
 *
 */
public enum MenuEnum {
	/** 菜单 **/
	MENU(1, "菜单"),

	/** 页面 */
	PAGE(2, "页面"),
	/** 按钮 **/
	BUTTON(3, "按钮");
	private MenuEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

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

}
