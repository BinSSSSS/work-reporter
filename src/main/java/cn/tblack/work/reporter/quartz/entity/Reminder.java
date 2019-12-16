package cn.tblack.work.reporter.quartz.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.tblack.work.reporter.constant.CustomBoolean;
import cn.tblack.work.reporter.constant.DataBaseBeanNames;

@Entity
@Table(schema = DataBaseBeanNames.DB_QUARTZ)
public class Reminder implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String userId;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Schedule.class,
			// 级联实体删除操作
			cascade = { CascadeType.REMOVE})
	private Schedule schedule; // 调度任务，一对一的关系
//	private Integer scheduleId;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = MailSender.class,
			// 级联实体删除操作
			cascade = { CascadeType.REMOVE })
	private MailSender mailSender;

	private Date createTime;

	@Column(name = "is_deprecated")
	private Short deprecated =  CustomBoolean.FALSE;

	private Date finishedTime;

	private Integer finishedCount = 0;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Short getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(Short deprecated) {
		this.deprecated = deprecated;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Integer getFinishedCount() {
		return finishedCount;
	}

	public void setFinishedCount(Integer finishedCount) {
		this.finishedCount = finishedCount;
	}
	
	

	@Override
	public String toString() {
		return "Reminder [id=" + id + ", userId=" + userId + ", schedule=" + schedule + ", mailSender=" + mailSender
				+ ", createTime=" + createTime + ", deprecated=" + deprecated + ", finishedTime=" + finishedTime
				+ ", finishedCount=" + finishedCount + "]";
	}
	
	
}
