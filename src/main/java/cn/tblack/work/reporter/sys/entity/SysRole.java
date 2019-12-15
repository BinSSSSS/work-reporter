package cn.tblack.work.reporter.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import cn.tblack.work.reporter.constant.DataBaseBeanNames;

@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
public class SysRole implements Serializable {
	
	@Id
	private String id;

    private String state;

    private String name;
    
    @Column(name = "role_key")
    private String roleKey;

    private String description;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_modified")
    private Date gmtModified;

    @Column(name = "parent_id")
    private String parentId;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "parent_id")
//    private SysRole parentRole;
    
    /**非数据库字段*/
    @Transient
    private String parentName;
    
    /**表示需要该角色才能访问的资源链接id*/
    @Transient
    private List<String> resIdList;
     
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey == null ? null : roleKey.trim();
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<String> getResIdList() {
		return resIdList;
	}

	public void setResIdList(List<String> resIdList) {
		this.resIdList = resIdList;
	}

	@Override
	public String toString() {
		return "SysRole [id=" + id + ", state=" + state + ", name=" + name + ", roleKey=" + roleKey + ", description="
				+ description + ", isDelete=" + isDelete + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", parentId=" + parentId + ", parentName=" + parentName + ", resIdList=" + resIdList + "]";
	}

//    public SysRole getParentRole() {
//        return parentRole;
//    }
//
//	public void setParentRole(SysRole parentRole) {
//		this.parentRole = parentRole;
//	}
 
	
}