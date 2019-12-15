package cn.tblack.work.reporter.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * <span>验证码绘制类</span>
 * @author TD唐登
 * @Date:2019年6月13日
 * @Version: 1.0(测试版)
 */
public class Captcha {

	private int width;
	private int height;
	private String code;  //表示生成的验证码
	private int len;	//验证码的长度
	
	
	

	private static Captcha captcha;	//存放着当前类的对象（用于产生单例类）
	private final static char[] DIGIT;	//存放着将要产生的数字
	private final static char[] ALPHABET; //存放着将要产生的验证码的字母
	private static Random rand = new Random(); //随机数对象
	private static final int DEFAULT_LEN = 5;  //默认的验证码生成个数
	
	private static int COLOR_BOUND = 0x9BFFFF;	//产生的随机颜色的最大值
	
	private static final  int DEFAULT_WIDTH = 40; //默认宽度
	private static final  int DEFAULT_HEIGHT = 110;  //默认高度
	private static final int LINE_NUM = 2;	//斜线的数量
	
	static {
		captcha = new Captcha();
		DIGIT =  DigitGenerator.numbers();
		ALPHABET = DigitGenerator.alphabet();
	}
	
	private Captcha() {
		init(DEFAULT_HEIGHT,DEFAULT_WIDTH);
		setLen(DEFAULT_LEN);
	}
	
	/**
	 * @ 单例设计模式， 只创建一个绘制验证码的对象
	 * @return
	 */
	public static Captcha getInstance() {
		return captcha;
	}
	
	/**
	 * @ 初始化对象， 设置产生验证码的宽度和高度。并生成随机的验证码
	 * @param width
	 * @param height
	 */
	public void init(int width, int height) {
		this.width = width;
		this.height =  height;
		setCode(randomCode());
	}
	
	/**
	 * @ 完全初始化， 设置所有的参数
	 * @param width
	 * @param height
	 * @param len
	 * @param code
	 */
	public void init(int width, int height, int len, String code) {
		this.width = width;
		this.height =  height;
		setLen(len);
		setCode(code);
	}
	
	
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	/**
	 * @ -生成一个随机的验证码。 长度默认为4
	 * @return
	 */
	public String randomCode() {
		
		return randomCode(len);
	}
	/**
	 * @ 产生指定长度的验证码
	 * @param len
	 * @return
	 */
	public String randomCode(int len) {
		
		char[] vCode = new char[len];
		
		for(int i  = 0 ;i < len; ++i) {
			
			/*@ 偶数生成字母*/
			if(i % 2 == 0) {
				vCode[i] = ALPHABET[rand.nextInt(ALPHABET.length)];
			}
			/*@ 奇数生成数字*/
			else {
				vCode[i] = DIGIT[rand.nextInt(DIGIT.length)];
			}
		}
		
		return new String(vCode);
	}
	
	public BufferedImage drawCheckImg() {
		return drawCheckImg(code);
	}
	
	/**
	 * @ 根据提供的code对象来绘制一张验证码图片
	 * @param code
	 * @return
	 */
	public BufferedImage drawCheckImg(String code) {
		
		BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		
		//获取图片对象的画笔
		Graphics2D graphics = img.createGraphics();  
		
		
		/*@ 设置画笔的颜色和背景填充色*/
		graphics.setBackground(Color.white);
		graphics.fillRect(0, 0, width, height);
//		graphics.setColor(Color.black);		//画边框线- 可以去除，更加美观
//		graphics.drawRect(0, 0, width - 1, height - 1);
		Font font = new Font("Consolas",Font.BOLD + Font.ITALIC,height);
		graphics.setFont(font);
		
		
		/*@ 画验证码*/
		for(int i = 0; i < len; ++i) {
			graphics.setColor(randColor(COLOR_BOUND));
			graphics.drawString(String.valueOf(code.charAt(i)), i * (width / (len + 1)) + 5, (int)(height * 0.8));
		}
		
		
		/*@ 画点（用于模糊图片）*/
		for(int i = 0; i < width + height; ++i) {
			graphics.setColor(randColor(0xFFFFF));
			graphics.drawOval(rand.nextInt(width),rand.nextInt(height),1,1);
		}
		
		/*@ 画线（用于模糊图片)*/
		for(int i  = 0; i < LINE_NUM; ++i) {
			graphics.setColor(randColor(0xFFFFF));
			graphics.drawLine(0, rand.nextInt(height), width, rand.nextInt(height));
		}
		
		return img;
	}
	
	/**
	 * @ 从给定的范围内生成随机的颜色
	 * @param rgb
	 * @return
	 */
	private Color randColor(int rgb) {
		return new Color(rand.nextInt(rgb));
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		Captcha captcha = Captcha.getInstance();
		captcha.init(150, 50);
	
		FileOutputStream  out = new FileOutputStream("code.jpg");
		ImageIO.write(captcha.drawCheckImg(), "jpg", out);
		out.close();
		
		
	}
	
	
	
}
