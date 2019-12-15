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

import cn.tblack.work.reporter.util.Captcha;
import cn.tblack.work.reporter.util.VerifyCodeUtils;

@Controller
public class KaptchaController {

//	@Autowired
//	private DefaultKaptcha kaptcha;

	@RequestMapping("/kaptcha")
	public void sendKaptcha(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {

//		String text = kaptcha.createText();
//		BufferedImage image = kaptcha.createImage(text);
		
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
