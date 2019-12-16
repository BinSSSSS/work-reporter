package cn.tblack.work.reporter.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.tblack.work.reporter.constant.DataBaseBeanNames;

@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
public class SysResources implements Serializable {

	@Id
	private String id;

	@JsonIgnore		//解决异常报错:Could not read JSON: failed to lazily initialize a collection, could not initialize proxy 
	@ManyToMany(targetEntity = SysRole.class, fetch = FetchType.EAGER,
			// 级联实体删除操作
			cascade = {CascadeType.REMOVE})

	// 级联查询通过外键进行操作,inverseJoinColumns表示的就是外键
	@JoinTable(name = "sys_res_role", joinColumns = { @JoinColumn(name = "res_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	@OrderBy("id")
	private Set<SysRole> roles;

	private String name;

	@Column(name = "parent_id")
	private String parentId;

	private String resKey;

	private String type;

	private String resUrl;

	private Integer level;

	private String icon;

	private Integer isHide;

	private String description;

	private Integer isDelete;

	private Integer colorId;

	private Date gmtCreate;

	private Date gmtModified;

	private static final long serialVersionUID = 1L;

	/** 非数据库内的字段，表示该资源的所属父节点 */
	@Transient
	private String parentName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey == null ? null : resKey.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl == null ? null : resUrl.trim();
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getColorId() {
		return colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
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

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		return "SysResources [id=" + id + ", roles=" + roles + ", name=" + name + ", parentId=" + parentId + ", resKey="
				+ resKey + ", type=" + type + ", resUrl=" + resUrl + ", level=" + level + ", icon=" + icon + ", isHide="
				+ isHide + ", description=" + description + ", isDelete=" + isDelete + ", colorId=" + colorId
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", parentName=" + parentName + "]";
	}

}