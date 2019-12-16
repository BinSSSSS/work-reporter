package cn.tblack.work.reporter.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.tblack.work.reporter.constant.DataBaseBeanNames;
import cn.tblack.work.reporter.util.excel.ExcelVOAttribute;

@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
@DynamicInsert()  //动态插入-空字段不插入
@DynamicUpdate	//动态更新-空字段不更新
public class SysUser implements Serializable {

	@Id
	@ExcelVOAttribute(name = "编号",column = "A")
	private String id;

	@ExcelVOAttribute(name = "用户名",column = "B", isExport = true)
	private String username;
	
	@ExcelVOAttribute(name = "手机号",column = "D", isExport = true)
	private String phone;

	@ExcelVOAttribute(name = "邮箱",column = "E", isExport = true)
	private String email;

	@ExcelVOAttribute(name = "状态",column = "F", isExport = true)
	private String state;

	@ExcelVOAttribute(name = "密码",column = "C", isExport = true,prompt = "密码不可见")
	private String password;

	@ExcelVOAttribute(name = "创建时间",column = "G", isExport = true)
	@Column(name = "create_time")
	private String createTime;

	@Column(name = "is_delete")
	private Integer isDelete;
	
	
	@JsonIgnore
	@ManyToMany(targetEntity = SysRole.class, fetch = FetchType.EAGER,
			// 级联实体持久化操作和级联实体合并操作
			cascade = { CascadeType.REMOVE,CascadeType.REFRESH,CascadeType.MERGE })
	// 级联查询通过外键进行操作,inverseJoinColumns表示的就是外键
	@JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })
	@OrderBy("id")
	private Set<SysRole> roles;

	@Column(name = "gmt_create")
	private Date gmtCreate;

	@Column(name = "gmt_modified")
	private Date gmtModified;

	@Column(name = "credentials_salt")
	private String credentialsSalt;

	private static final long serialVersionUID = 1L;

	/** 不是数据库内的字段 */
	@Transient
	private List<String> roleIds  = new ArrayList<>();


	public SysUser() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
		setRoleIds();
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

	public String getCredentialsSalt() {
		return credentialsSalt;
	}

	public void setCredentialsSalt(String credentialsSalt) {
		this.credentialsSalt = credentialsSalt;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}
	
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * ~-~将Set<Role>对象转换为 包含roleKey对象的数组
	 */
	public void setRoleIds() {
		roleIds.clear();
		if(roles ==  null)
			return;
		for (SysRole role : roles) {
			roleIds.add(role.getId());
		}
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", username=" + username + ", phone=" + phone + ", email=" + email + ", state="
				+ state + ", password=" + password + ", createTime=" + createTime + ", isDelete=" + isDelete
				+ ", roles=" + roles + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", credentialsSalt=" + credentialsSalt + ", roleIds=" + roleIds + "]";
	}

}