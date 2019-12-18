package cn.tblack.work.reporter.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.annotation.HasAdminRole;
import io.swagger.annotations.Api;

@Api(tags = "管理员操作控制器" ,hidden = true)
@RestController
@RequestMapping("/admin")
@HasAdminRole
public class RestAdminController {


}
