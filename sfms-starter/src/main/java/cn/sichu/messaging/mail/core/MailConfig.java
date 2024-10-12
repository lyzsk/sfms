package cn.sichu.messaging.mail.core;

import cn.hutool.core.map.MapUtil;
import cn.sichu.core.utils.validate.ValidationUtils;
import lombok.Getter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Getter
public class MailConfig {

    public static final String DEFAULT_PROTOCOL = "smtp";
    private static final Charset DEFAULT_CHARSET;

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
    }

    private final Map<String, String> properties;
    /**
     * 协议
     */
    private String protocol = DEFAULT_PROTOCOL;
    /**
     * 服务器地址
     */
    private String host;
    /**
     * 服务器端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码（授权码）
     */
    private String password;
    /**
     * 发件人
     */
    private String from;
    /**
     * 是否启用 SSL 连接
     */
    private boolean sslEnabled = false;
    /**
     * SSL 端口
     */
    private Integer sslPort;
    private Charset defaultEncoding;

    public MailConfig() {
        this.defaultEncoding = DEFAULT_CHARSET;
        this.properties = MapUtil.newHashMap();
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public void setSslPort(Integer sslPort) {
        this.sslPort = sslPort;
    }

    public void setDefaultEncoding(Charset defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    /**
     * 将当前配置转换为 JavaMail 的 Properties 对象
     *
     * @return Properties 对象
     */
    public Properties toJavaMailProperties() {
        Properties javaMailProperties = new Properties();
        javaMailProperties.putAll(this.getProperties());
        javaMailProperties.put("mail.from", this.getFrom());
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.ssl.enable", this.isSslEnabled());
        if (this.isSslEnabled()) {
            ValidationUtils.throwIfNull(this.getSslPort(), "邮件配置错误：SSL端口不能为空");
            javaMailProperties.put("mail.smtp.socketFactory.port", this.sslPort);
            javaMailProperties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        }
        return javaMailProperties;
    }
}