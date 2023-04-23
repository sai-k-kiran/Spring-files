package main;

import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.helloService;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ProjectConfig.class);

        helloService hello = context.getBean(helloService.class);

        String mssg = hello.helloName("John");
        System.out.println("mssg: " + mssg);
    }
}
