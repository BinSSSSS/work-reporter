package cn.tblack.work.reporter.social.qq.model;

import java.io.Serializable;

/**
 * @~_~_通过第三方授权登录拿到的QQ用户基本信息。 @author TD唐登
 * @Date:2019年12月11日
 * @Version: 1.0(测试版)
 */
public class QQUserInfo implements Serializable{


	private static final long serialVersionUID = 1L;

	/** 返回值 */
	private String ret;

	/** 返回信息。如果出错 ，则为出错信息 */
	private String msg;

	/** 开放访问id */
	private String openid;

	/** 省份 */
	private String province;

	/** 城市 */
	private String city;

	/** 出生年月 */
	private String year;

	/** 昵称 */
	private String nickname;

	/** 空间头像100*100 */
	private String figureurl_2;

	/** 空间头像40*40 */
	private String figureurl_1;

	/** QQ头像100*100 */
	private String figureurl_qq_2;

	/** QQ头像40*40 */
	private String figureurl_qq_1;

	/** 性别。默认为男 */
	private String gender;

	/** VIP等级 */
	private String level;

	/** 是否是VIP。 0 不是，1 是 */
	private String vip;

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFigureurl_2() {
		return figureurl_2;
	}

	public void setFigureurl_2(String figureurl_2) {
		this.figureurl_2 = figureurl_2;
	}

	public String getFigureurl_1() {
		return figureurl_1;
	}

	public void setFigureurl_1(String figureurl_1) {
		this.figureurl_1 = figureurl_1;
	}

	public String getFigureurl_qq_2() {
		return figureurl_qq_2;
	}

	public void setFigureurl_qq_2(String figureurl_qq_2) {
		this.figureurl_qq_2 = figureurl_qq_2;
	}

	public String getFigureurl_qq_1() {
		return figureurl_qq_1;
	}

	public void setFigureurl_qq_1(String figureurl_qq_1) {
		this.figureurl_qq_1 = figureurl_qq_1;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	@Override
	public String toString() {
		return "QQUserInfo [ret=" + ret + ", msg=" + msg + ", openid=" + openid + ", province=" + province + ", city="
				+ city + ", year=" + year + ", nickname=" + nickname + ", figureurl_2=" + figureurl_2 + ", figureurl_1="
				+ figureurl_1 + ", figureurl_qq_2=" + figureurl_qq_2 + ", figureurl_qq_1=" + figureurl_qq_1
				+ ", gender=" + gender + ", level=" + level + ", vip=" + vip + "]";
	}

}
