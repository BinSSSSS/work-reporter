package cn.tblack.work.reporter.sys.entity;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.tblack.work.reporter.constant.DataBaseBeanNames;
import cn.tblack.work.reporter.oss.enums.OssType;

/**
 * @___!_!实体类，用于表示于存储桶的配置信息
 * @author TD唐登
 * @Date:2019年12月9日
 * @Version: 1.0(测试版)
 */
@Entity
@Table(schema = DataBaseBeanNames.DB_SYSTEM)
public class OssConfig implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String appId;

	private String bucketName;

	private String region;

	private String domain;

	private String secretId;

	private String secretKey;

	private String pathPrefix;

	private String description;

	@Convert(converter = OssType.Converter.class)
	private OssType type;  //将数值类型转换为枚举类型
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public void setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public OssType getType() {
		return type;
	}

	public void setType(OssType type) {
		this.type = type;
	}
	public void setType(int type) {
		this.type = OssType.valueOf(type);
	}

	@Override
	public String toString() {
		return "OssConfig [id=" + id + ", appId=" + appId + ", bucketName=" + bucketName + ", region=" + region
				+ ", domain=" + domain + ", secretId=" + secretId + ", secretKey=" + secretKey + ", pathPrefix="
				+ pathPrefix + ", description=" + description + ", type=" + type + "]";
	}

}
