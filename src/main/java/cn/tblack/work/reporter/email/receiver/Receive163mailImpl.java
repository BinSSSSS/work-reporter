package cn.tblack.work.reporter.email.receiver;


import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.springframework.boot.autoconfigure.mail.MailProperties;

import cn.tblack.work.reporter.email.MyEmailProperties;

import static cn.tblack.work.reporter.email.MyEmailProperties.*;


public class Receive163mailImpl implements ReceiveMailInter {
    
	private MailProperties mailProperties  = MyEmailProperties.getMailProperties();
	
    @Override
    public Message[] receiveMail() throws Exception{
        // 定义连接POP3服务器的属性信息
//        String pop3Server = EmailConstant.POP163SERVER;
//        String protocol = EmailConstant.PROTOCOL;
//        String username = EmailConstant.getEmailaccount();
//        String password = EmailConstant.getEmailpwd();
        // QQ邮箱的SMTP的授权码，什么是授权码，它又是如何设置？
        Properties props = new Properties();
        
        props.putAll(mailProperties.getProperties());

        // 发件人的邮箱的 SMTP服务器地址
        // 获取连接
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);
        // 获取Store对象
        Store store = session.getStore(MyEmailProperties.MAIL_SMTP_PROTOCOL);
        store.connect(MAIL_SMTP_HOST_163,Integer.parseInt(MAIL_SMTP_PORT),
        		mailProperties.getUsername(), mailProperties.getPassword());
        
        // POP3服务器的登陆认证
        // 通过POP3协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
        Folder folder = store.getFolder("INBOX");
        // 获得用户的邮件帐户
        folder.open(Folder.READ_WRITE);
        // 设置对邮件帐户的访问权限
        Message[] messages = folder.getMessages();
        // 得到邮箱帐户中的所有邮件
        folder.close(false);
        // 关闭邮件夹对象
        store.close();
        // 关闭连接对象
        return messages;
    }
}
