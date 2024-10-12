package cn.sichu.core.autoconfigure.project;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 项目自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@AutoConfiguration
@ComponentScan("cn.hutool.extra.spring")
@Import(cn.hutool.extra.spring.SpringUtil.class)
@EnableConfigurationProperties(ProjectProperties.class)
public class ProjectAutoConfiguration {
}
