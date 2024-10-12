package cn.sichu.core.autoconfigure.project;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
@ConfigurationProperties("project")
public class ProjectProperties {

    /**
     * 名称
     */
    private String name;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 版本
     */
    private String version;

    /**
     * 描述
     */
    private String description;

    /**
     * URL
     */
    private String url;

    /**
     * 基本包
     */
    private String basePackage;

    /**
     * 联系人
     */
    private Contact contact;

    /**
     * 许可协议
     */
    private License license;

    /**
     * 是否为生产环境
     */
    private boolean production = false;

    /**
     * 联系人配置属性
     */
    public static class Contact {
        /**
         * 名称
         */
        private String name;

        /**
         * 邮箱
         */
        private String email;

        /**
         * URL
         */
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    /**
     * 许可协议配置属性
     */
    public static class License {
        /**
         * 名称
         */
        private String name;

        /**
         * URL
         */
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
