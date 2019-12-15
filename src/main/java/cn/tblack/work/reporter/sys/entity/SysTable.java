package cn.tblack.work.reporter.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.tblack.work.reporter.constant.DataBaseBeanNames;

@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
public class SysTable implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String tableName;

	private String comment;

	private String className;

	private Integer pkColumn;

	private String engine;

	private Date createTime;

	private String tableSchema;

	private Date datetime;

	private String packageName;

	private String pathName;

	private String entityName;

	private static final long serialVersionUID = 1L;

	@Transient
	private SysColumn pk;

	@Transient
	private List<SysColumn> columns;

	@Transient
	private String email;
	

	@Transient
	private String author;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment == null ? null : comment.trim();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className == null ? null : className.trim();
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine == null ? null : engine.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Integer getPkColumn() {
		return pkColumn;
	}

	public void setPkColumn(Integer pkColumn) {
		this.pkColumn = pkColumn;
	}

	public SysColumn getPk() {
		return pk;
	}

	public void setPk(SysColumn pk) {
		this.pk = pk;
	}

	public List<SysColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<SysColumn> columns) {
		this.columns = columns;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}