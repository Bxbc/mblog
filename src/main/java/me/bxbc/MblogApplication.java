package me.bxbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class MblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MblogApplication.class, args);
    }

}
