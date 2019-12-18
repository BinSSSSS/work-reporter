package cn.tblack.work.reporter.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.tblack.work.reporter.util.Captcha;
import cn.tblack.work.reporter.util.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "图片验证码绘制")
@Controller
public class KaptchaController {

	
	@ApiOperation(value ="发送图片验证码",notes = "主要用于前端验证时图片验证码的绘制")
	@RequestMapping(value = "/kaptcha",method = {RequestMethod.POST,RequestMethod.GET})
	public void sendKaptcha(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {

		Captcha captcha = Captcha.getInstance();
		String code  = captcha.randomCode();
		BufferedImage image = captcha.drawCheckImg(code);
		
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		ImageIO.write(image, "jpg", jpegOutputStream);
		responseOutputStream.write(jpegOutputStream.toByteArray());
		responseOutputStream.flush();
		responseOutputStream.close();
		
		session.setAttribute(VerifyCodeUtils.VCODE_NAME, code);
	}
}
