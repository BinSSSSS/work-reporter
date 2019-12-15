package cn.tblack.work.reporter.report.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author tblack
 * @email 1395926989@qq.com
 * @date Tue Dec 10 15:48:57 CST 2019
 */
@Entity
public class ReportColumn implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String colName;
	
	private String colKey;

	private Integer colNum;

	private String reportId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColKey() {
		return colKey;
	}

	public void setColKey(String colKey) {
		this.colKey = colKey;
	}

	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

}