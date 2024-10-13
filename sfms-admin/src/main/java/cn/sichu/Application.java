package cn.sichu;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.sichu.core.autoconfigure.project.ProjectProperties;
import cn.sichu.crud.core.annotation.EnableCrudRestController;
import cn.sichu.web.annotation.EnableGlobalResponse;
import cn.sichu.web.model.R;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sichu huang
 * @date 2024/10/06
 **/
@Slf4j
@EnableFileStorage
@EnableMethodCache(basePackages = "cn.sichu")
@EnableGlobalResponse
@EnableCrudRestController
@RestController
@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = "cn.sichu")
@AutoConfigurationPackage(basePackages = "cn.sichu")
public class Application implements ApplicationRunner, ApplicationContextAware {
    private static ApplicationContext context;
    private final ProjectProperties projectProperties;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.err.println(context.getAutowireCapableBeanFactory());
    }

    @Hidden
    @SaIgnore
    @GetMapping("/")
    public R index() {
        return R.ok("%s service started successfully.".formatted(projectProperties.getName()),
            null);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println(" ____  _____ __  __ ____  ");
        System.out.println("/ ___||  ___|  \\/  / ___| ");
        System.out.println("\\___ \\| |_  | |\\/| \\___ \\ ");
        System.out.println(" ___) |  _| | |  | |___) |");
        System.out.println("|____/|_|   |_|  |_|____/ \n");

        System.out.println(
            String.format(" Author: %s                 Github: %s                 (v%s)\n",
                projectProperties.getContact().getName(), projectProperties.getContact().getUrl(),
                projectProperties.getVersion()));
        System.out.println(
            " =========================== Start Successfully! ===========================\n");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
