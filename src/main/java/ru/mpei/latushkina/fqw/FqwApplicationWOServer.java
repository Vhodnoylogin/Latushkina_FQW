package ru.mpei.latushkina.fqw;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FqwApplicationWOServer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FqwApplicationWOServer.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        var context = app.run(args);
//        var comtradeUploadService = context.getBean(ComtradeUploadService.class);
//        comtradeUploadService.upload(null,null);
    }
}
