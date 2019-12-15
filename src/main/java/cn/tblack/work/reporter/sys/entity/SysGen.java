package cn.tblack.work.reporter.sys.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.tblack.work.reporter.constant.DataBaseBeanNames;

@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
public class SysGen {

	@Id
	private String key;

	private String value;

	private Date gmtCreate;

	private Date gmtModified;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public String toString() {
		return "SysGen [key=" + key + ", value=" + value + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ "]";
	}

}
