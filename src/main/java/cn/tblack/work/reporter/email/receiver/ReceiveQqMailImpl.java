package cn.tblack.work.reporter.email.receiver;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

import org.springframework.boot.autoconfigure.mail.MailProperties;

import cn.tblack.work.reporter.email.MyEmailProperties;

import static  cn.tblack.work.reporter.email.MyEmailProperties.*;


/**
 * @-用于接收到来自QQ邮箱的邮件信息
 * @author TD唐登
 * @Date:2019年11月20日
 * @Version: 1.0(测试版)
 */
public class ReceiveQqMailImpl extends AbstractReceiveMail {
    
	
	public  MailProperties mailProperties = MyEmailProperties.getMailProperties();  
	
    @Override
    public Store getStore(Session session) throws Exception{
        Store store = session.getStore(MAIL_SMTP_PROTOCOL);
        store.connect(mailProperties.getHost(), mailProperties.getPort(),
        		mailProperties.getUsername(),mailProperties.getPassword());
       return store;
    }
    
    @Override
    public Properties getProperties(){

    	Properties prop = new Properties();
    	
    	prop.putAll(mailProperties.getProperties());
    	
    	return prop;
    }
    
    @Override
    public Folder getFolder(Store store) throws Exception{
        Folder inbox = null;
        inbox = store.getFolder(MAIL_RECEIVER_FLODER);
        inbox.open(Folder.READ_ONLY);
        return inbox;
    }
    
    @Override
    public Session getSession(Properties pros){
        Session session = Session.getDefaultInstance(pros, null);
        session.setDebug(false);
        return session;
    }
}
