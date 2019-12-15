package cn.tblack.work.reporter.oss.utils;

import cn.tblack.work.reporter.sys.entity.OssConfig;

/**
 * @_!_!_用于拼接一个存储桶内对象的地址
 * @author TD唐登
 * @Date:2019年12月9日
 * @Version: 1.0(测试版)
 */
public class OssObjectUrlUtil {

	/**
	 * @~——~通过传递一个配置信息和一个用于标记文件的key对象，来拼接一个腾讯云的对象地址
	 * @param config 存储桶配置信息
	 * @param key 文件对象的key信息
	 * @return
	 */
	public static String concatQCloudObjectUrl(OssConfig config,String key) {
		StringBuffer buffer = new StringBuffer("https://");
		
		buffer.append(config.getBucketName() + ".cos." +  config.getRegion() + ".myqcloud.com/" + key);
		
		return buffer.toString();
	}
}
