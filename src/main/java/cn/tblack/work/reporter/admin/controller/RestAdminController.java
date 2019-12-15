package cn.tblack.work.reporter.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.annotation.HasAdminRole;

@RestController
@RequestMapping("/admin")
@HasAdminRole
public class RestAdminController {


}
