package cn.tblack.work.reporter.res.controller;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.SysResources;
import cn.tblack.work.reporter.sys.service.SysResourcesService;
import cn.tblack.work.reporter.util.DatabaseTableIdGenerator;

@RestController
//@HasAdminRole
@RequestMapping("res")
public class RestResourceController {
	
	private static Logger log =  LoggerFactory.getLogger(RestResourceController.class);
	
	@Autowired
	private SysResourcesService resService;
	
	@RequestMapping(value = "res-list")
	public List<SysResources> resourcesList(@RequestParam(name = "resUrl",defaultValue = "")String resUrl) {
		
		List<SysResources> resList =  null;
		try {
//			resList =  resService.findAll();
			resList  = resService.fuzzyFindByResUrl(resUrl);
			
			
		}catch(Exception e) {
			log.error("查询资源列表出错，出错原因为: " + e.getMessage());
			e.printStackTrace();
		}
		return resList;
	}
	
	/**
	 * @~__~拿到指定id的资源信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get")
	public SysResources getResources(String id) {
		
		SysResources res = null;
		
		try {
			
			res = resService.findById(id);
			
			//查找父级元素
			String parentId = res.getParentId();
			if(parentId != null &&  !Strings.isEmpty(parentId)) {
				SysResources parentRes =  resService.findById(parentId);
				if(parentRes != null)
					res.setParentName(parentRes.getName());
			}
	
		}catch(Exception e) {
			e.printStackTrace();
			log.error("查询资源信息出错~出错信息为：" + e.getMessage());
		}
		
		
		return res;
	}
	
	/**
	 * @~___!_!更新指定资源的信息
	 * @return
	 */
	@PostMapping(value = "/update")
	public WebResult updateResources(@RequestBody SysResources sysRes) {
	
		WebResult result = new WebResult();
		
		log.info("传递的更新资源信息为: " +  sysRes);
		
		try { 
			//更新资源-因为资源信息没有敏感信息， 可以直接进行更新
			resService.save(sysRes);
			result.setMsg("更新成功");
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("更新资源[ " + sysRes.getId() +"]的信息出错，出错信息为: " + e.getMessage());
			result.setMsg("更新失败~服务器正忙~");
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	@PostMapping(value = "/delete")
	public WebResult deleteResources(String resId) {
		
		WebResult result = new WebResult();
		
		try {
			resService.deleteById(resId);
			result.setMsg("删除成功");
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("删除资源出错，出错原因为: " +  e.getMessage());
			result.setMsg("删除失败~服务器正忙~");
			result.setSuccess(false);
		}
		
		return result;
		
	}
	/**
	 * @!_!—— !_! 插入一条新的资源记录 
	 * @param res
	 * @return
	 */
	@PostMapping(value = "/insert")
	public WebResult addResources(@RequestBody SysResources res) {
		
		WebResult result = new WebResult();
		
		log.info("新添加的资源信息为: " +  res);
		
		try {
			res.setId(DatabaseTableIdGenerator.createResId(resService));
			resService.save(res);
			result.setMsg("添加成功");
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("创建一条新的资源记录出错，出错原因为: " + e.getMessage());
			result.setMsg("添加失败~服务器正忙~");
			result.setSuccess(false);
		}
		
		
		return result;
	}
	
}
