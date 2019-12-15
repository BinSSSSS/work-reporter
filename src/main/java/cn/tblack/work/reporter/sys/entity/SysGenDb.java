package cn.tblack.work.reporter.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.tblack.work.reporter.constant.DataBaseBeanNames;

@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
public class SysGenDb implements Serializable {
	
	@Id
	private String id;

	@Column(name = "db_name")
	private String dbName;

	@Column(name = "db_url")
	private String dbUrl;

	@Column(name = "db_username")
	private String dbUsername;

	@Column(name = "db_pwd")
	private String dbPwd;

	private String description;
	
	@Column(name = "gmt_create")
	private Date gmtCreate;

	@Column(name = "gmt_modified")
	private Date gmtModified;

	@Column(name = "db_class_driver")
	private String dbClassDriver;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName == null ? null : dbName.trim();
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl == null ? null : dbUrl.trim();
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername == null ? null : dbUsername.trim();
	}

	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd == null ? null : dbPwd.trim();
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

	public String getDbClassDriver() {
		return dbClassDriver;
	}

	public void setDbClassDriver(String dbClassDriver) {
		this.dbClassDriver = dbClassDriver == null ? null : dbClassDriver.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "SysGenDb [id=" + id + ", dbName=" + dbName + ", dbUrl=" + dbUrl + ", dbUsername=" + dbUsername
				+ ", dbPwd=" + dbPwd + ", description=" + description + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + ", dbClassDriver=" + dbClassDriver + "]";
	}
	
}