package config;

import Beans.Cat;
import Beans.Owner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"services", "repository"})

public class ProjectConfig {
    @Bean
    public Cat cat(){
        Cat c = new Cat();
        c.setName("Cat");
        return c;
    }
    @Bean
    public Cat cat1(){
        Cat c = new Cat();
        c.setName("Cat1");
        return c;
    }

    @Bean
    public Owner owner(@Qualifier("cat1") Cat cat){
        Owner man = new Owner();
        man.setCat(cat); // the cat1() method created above is passed as param here.
        return man;
    }
}
