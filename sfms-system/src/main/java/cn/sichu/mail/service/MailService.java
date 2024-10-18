package cn.sichu.mail.service;

/**
 * @author sichu huang
 * @since 2024/10/17 16:52
 */
public interface MailService {
    
    /**
     * 发送简单文本邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param text    邮件内容
     * @author sichu huang
     * @since 2024/10/17 16:52:59
     */
    void sendMail(String to, String subject, String text);

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人地址
     * @param subject  邮件主题
     * @param text     邮件内容
     * @param filePath 附件路径
     * @author sichu huang
     * @since 2024/10/17 16:53:11
     */
    void sendMailWithAttachment(String to, String subject, String text, String filePath);
}
