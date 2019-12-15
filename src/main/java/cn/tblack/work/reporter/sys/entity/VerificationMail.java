package cn.tblack.work.reporter.sys.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import cn.tblack.work.reporter.constant.DataBaseBeanNames;
import cn.tblack.work.reporter.enums.VCodeEmailTypes;

/**
 * @存放发送验证码信息
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
public class VerificationMail implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Email
	@Column(name = "recipient_mail")
	private String recipientMail;

	@Column(name = "create_time")
	private Date createTime;

	private String code;

	private Date deadline;

	private Long weights;
	
	@Convert(converter = VCodeEmailTypes.Converter.class)
	private VCodeEmailTypes type;  //表示发送邮件的类型

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Long getWeights() {
		return weights;
	}

	public void setWeights(Long weights) {
		this.weights = weights;
	}
	
	public String getRecipientMail() {
		return recipientMail;
	}
	public void setRecipientMail(String recipientMail) {
		this.recipientMail = recipientMail;
	}
	
	public VCodeEmailTypes getEmailType() {
		return type;
	}


	public void setType(VCodeEmailTypes type) {
		this.type = type;
	}

	
	@Override
	public String toString() {
		return "VerificationMail [id=" + id + ", recipientMail=" + recipientMail + ", createTime=" + createTime
				+ ", code=" + code + ", deadline=" + deadline + ", weights=" + weights + ", type=" + type + "]";
	}

	
}
