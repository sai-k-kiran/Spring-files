package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = {"services", "repository", "Beans"})

public class ProjectConfig {
//    @Bean
//    public Cat cat(){
//        Cat theo = new Cat();
//        theo.setName("Theo");
//        return theo;
//    }
//
//    @Bean
//    public Owner owner(){
//        Owner man = new Owner();
//        man.setCat(cat()); // the cat() method created above is passed as param here.
//        return man;
//    }
}
