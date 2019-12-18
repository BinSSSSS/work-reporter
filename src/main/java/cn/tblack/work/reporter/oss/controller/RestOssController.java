package cn.tblack.work.reporter.oss.controller;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import cn.tblack.work.reporter.oss.uploader.QCloudOssUploader;
import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.properties.WebConfigProperties;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.OssConfig;
import cn.tblack.work.reporter.sys.entity.SysOss;
import cn.tblack.work.reporter.sys.service.OssConfigService;
import cn.tblack.work.reporter.sys.service.SysOssService;
import cn.tblack.work.reporter.util.FileWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "存储桶资源相关控制器")
@RestController
@RequestMapping("/oss")
@NeedAnyRole
public class RestOssController {

	private static Logger log = LoggerFactory.getLogger(RestOssController.class);

	@Autowired
	private SysOssService ossService;

	@Autowired
	private OssConfigService ossConfigService;
	
	@ApiOperation("存储桶资源分页列表")
	@RequestMapping(value = "/page-list",method = {RequestMethod.POST,RequestMethod.GET})
	public LaYuiPage<SysOss> getPageList(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = "10") Integer limit) {

		LaYuiPage<SysOss> ossLPage = null;

		try {

			Pageable pageable = PageRequest.of(page - 1, limit);

			ossLPage = ossService.findAll(pageable);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询SysOss数据库出错，出错原因为: " + e.getMessage());
		}

		return ossLPage;

	}
	
	@ApiOperation(value = "文件的上传")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "configId", value = "存储桶配置id", dataTypeClass =  Integer.class, required = true),
		@ApiImplicitParam(name = "file", value = "上传文件", dataTypeClass =  MultipartFile.class, required = true)
	})
	@PostMapping(value = "/upload")
	public WebResult uploadFileToOss(Integer configId, @RequestParam("file") MultipartFile file) {

		WebResult result = new WebResult();

//		log.info("上传的文件名为:  " + file.getOriginalFilename() + ",配置id为: " + configId);

		try {

			// 查找该存储桶配置对象
			OssConfig config = ossConfigService.findById(configId);

			if (config == null) {
				result.setMsg("无法找到对应的存储桶配置~");
				result.setSuccess(false);
				return result;
			}

			File dir = new File(WebConfigProperties.UPLOAD_LOCATION);
			File localFile = new File(dir.getAbsolutePath() + file.getOriginalFilename());
			log.info("传递的文件大小为: "  + file.getSize());
			// 之前该文件夹不存在的话， 创建新的文件夹
			if (!dir.exists())
				dir.mkdirs();
			// 将数据写入到本地文件中
			FileWriter.writeToFile(file.getInputStream(), (int) file.getSize(), WebConfigProperties.WRITE_BUFFER_SIZE,
					localFile.getAbsolutePath());
			log.info("文件保存在本地成功!" + "路径为:  " + localFile.getAbsolutePath() );
			String objectUrl = null;
			// 进行文件上传的存储桶
			switch (config.getType()) {
			case ALIYUN:
				break;
			case QCLOUD:
				log.info("进行腾讯云存储桶的上传,上传桶名为:  " + config.getBucketName() + ",桶区域为: " +  config.getRegion());
				objectUrl = QCloudOssUploader.uploadFile(localFile, config);
				break;
			case QINIU:
				break;
			}
			// 删除上传文件
			localFile.delete();

			if (objectUrl != null) {

				// 保存该文件到数据库中
				SysOss resources = new SysOss();
				resources.setUrl(objectUrl);
				resources.setCreateDate(new Date());
				ossService.save(resources);

				result.setMsg("文件上传成功~");
				result.setSuccess(true);
			} else {
				result.setMsg("文件上传失败");
				result.setSuccess(false);
			}

		} catch (Exception e) {

			e.printStackTrace();
			log.error("上传文件到id为[" + configId + "]的储存桶失败！失败原因为: " + e.getMessage());
			result.setMsg("上传文件失败!服务器正忙~");
			result.setSuccess(false);
		}

		return result;
	}
	
	@ApiOperation(value = "删除多个存储资源")
	@ApiImplicitParam(name ="ids", value = "多个存储资源id", dataTypeClass = String.class, required = true)
	@PostMapping("/delete")
	public WebResult deleteOssResources(String ids) {
		
		WebResult result =  new WebResult();
		
		String[] idArr = ids.split(",");

		try {

			for (String idStr : idArr) {
				
				ossService.deleteById(Long.valueOf(idStr));
			}

			result.setMsg("删除成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除id为["+ ids +"]的SysGenOss的对象失败！失败原因为: " + e.getMessage());
			result.setMsg("删除对象失败，服务器正忙~");
			result.setSuccess(false);
		}
		
		return result;
		
	}
}
