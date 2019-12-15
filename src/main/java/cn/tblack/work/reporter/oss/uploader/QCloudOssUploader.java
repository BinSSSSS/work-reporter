package cn.tblack.work.reporter.oss.uploader;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import cn.tblack.work.reporter.oss.utils.OssObjectUrlUtil;
import cn.tblack.work.reporter.sys.entity.OssConfig;

/**
 * @~_~用于腾讯云OSS储存桶上传资源
 * @author TD唐登
 * @Date:2019年12月9日
 * @Version: 1.0(测试版)
 */
public class QCloudOssUploader {

	private static Logger log = LoggerFactory.getLogger(QCloudOssUploader.class);

	public static String uploadFile(File file, OssConfig config) {

		// 通过文件名作为上传对象的key
		String key = file.getName();

		// 生成存储桶验证对象
		COSCredentials cred = new BasicCOSCredentials(config.getSecretId(), config.getSecretKey());

		// 生成区域对象
		Region region = new Region(config.getRegion());

		// 生成客户端配置对象
		ClientConfig clientConfig = new ClientConfig(region);

		// 生成存储桶客户端
		COSClient cosClient = new COSClient(cred, clientConfig);

		// 进行文件的上传
		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(config.getBucketName(), key, file);
			PutObjectResult result = cosClient.putObject(putObjectRequest);
			
			log.info("文件上传到返回结果信息为: " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("上传文件[" + file.getName() + "]到腾讯存储失败，失败原因为: " + e.getMessage() + ",传递的配置信息为: " + config);
			return null;
		}finally {
			cosClient.shutdown();
		}

		return OssObjectUrlUtil.concatQCloudObjectUrl(config, key);
	}

}
