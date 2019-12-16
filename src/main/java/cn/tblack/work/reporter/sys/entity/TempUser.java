package cn.tblack.work.reporter.sys.entity;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.tblack.work.reporter.constant.DataBaseBeanNames;
import cn.tblack.work.reporter.social.enums.SocialPlatformType;

/**
 * @_!__!第三方登录用户表
 * @author TD唐登
 * @Date:2019年12月13日
 * @Version: 1.0(测试版)
 */
@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
@DynamicInsert
@DynamicUpdate
public class TempUser implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String openId;

	private String nickname;

	private String avatarUrl;

	@Convert(converter = SocialPlatformType.Converter.class)
	private SocialPlatformType socialPlatform;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public SocialPlatformType getSocialPlatform() {
		return socialPlatform;
	}

	public void setSocialPlatform(SocialPlatformType socialPlatform) {
		this.socialPlatform = socialPlatform;
	}

	@Override
	public String toString() {
		return "TempUser [id=" + id + ", openId=" + openId + ", nickname=" + nickname + ", avatarUrl=" + avatarUrl
				+ ", socialPlatform=" + socialPlatform + "]";
	}


}
