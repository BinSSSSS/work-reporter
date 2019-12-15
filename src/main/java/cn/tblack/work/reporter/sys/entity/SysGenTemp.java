package cn.tblack.work.reporter.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import cn.tblack.work.reporter.constant.DataBaseBeanNames;

@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
@DynamicInsert
public class SysGenTemp implements Serializable {
	
	@Id
	private String id;

	private String description;

	@Column(name = "gen_filename")
	private String genFilename;

	@Column(name = "gmt_create")
	private Date gmtCreate;

	@Column(name = "gmt_modified")
	private Date gmtModified;

	private Integer state;

	@Column(name = "style_id")
	private String styleId;

	@Column(name = "gen_context")
	private String genContext;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getGenFilename() {
		return genFilename;
	}

	public void setGenFilename(String genFilename) {
		this.genFilename = genFilename == null ? null : genFilename.trim();
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId == null ? null : styleId.trim();
	}

	public String getGenContext() {
		return genContext;
	}

	public void setGenContext(String genContext) {
		this.genContext = genContext == null ? null : genContext.trim();
	}

	@Override
	public String toString() {
		return "SysGenTemp [id=" + id + ", description=" + description + ", genFilename=" + genFilename + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + ", state=" + state + ", styleId=" + styleId
				+ ", genContext=" + genContext + "]";
	}
	
	
}