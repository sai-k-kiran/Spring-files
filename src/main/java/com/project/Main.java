package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
//        CustomerService custService = new CustomerService(new CustomerDAOService());
//        CustomerController cusController = new CustomerController(custService);
//        Never do it like this
        ConfigurableApplicationContext applicationContext =  // to get the beans from app context
                SpringApplication.run(Main.class, args);

        String[] beans = applicationContext.getBeanDefinitionNames(); // returns beans name as a string[]

        for(String bean : beans) {
            System.out.println(bean);
        }
    }

    @GetMapping("/greet")
    public String greet(){return "Hello";}

    @GetMapping("/json")
    public GreetResponse greet1(@RequestParam(value = "name", required = false) String name){

        String str = (name == null || name.isBlank()) ? "Generic" : name;

        return new GreetResponse("This is json",
                List.of("Java", "Python", "JS"),
                new Person(str, 26, "Software Engineer"));
    }

    record Person(String name, int age, String occ){}
    record GreetResponse(String res, List<String> langs, Person person){}
}

// @RequestParam("name") is for the URL params.
// ex: localhost:8080/json?name="Saik", here name="Saik" is a param passed to greet1() above
// then the person name would be "Saik", else person name = "Generic"
