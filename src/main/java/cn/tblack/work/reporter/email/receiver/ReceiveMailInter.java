package cn.tblack.work.reporter.email.receiver;


import javax.mail.Message;


public interface ReceiveMailInter {
    
    /**
     * 接受邮件
     * 
     * @return
     * @throws Exception
     */
    Message[] receiveMail() throws Exception;
}
