package cn.sichu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author sichu huang
 * @since 2024/10/16 21:18
 */
@EnableScheduling
@ConfigurationPropertiesScan
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println(" ____  _____ __  __ ____  ");
        System.out.println("/ ___||  ___|  \\/  / ___| ");
        System.out.println("\\___ \\| |_  | |\\/| \\___ \\ ");
        System.out.println(" ___) |  _| | |  | |___) |");
        System.out.println("|____/|_|   |_|  |_|____/ \n");

        // System.out.println(
        //     String.format(" Author: %s                 Github: %s                 (v%s)\n",
        //         projectProperties.getContact().getName(), projectProperties.getContact().getUrl(),
        //         projectProperties.getVersion()));
        System.out.println(
            " =========================== Start Successfully! ===========================\n");
    }
}
