package me.bxbc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Author: BI XI
 * Date 2021/2/21
 */

// 打包成war包时候需要新增这一个类，将服务器的启动类定向到 Spring 框架中启动类
public class SpringBootStartApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MblogApplication.class);
    }
}
