package com.project;

import com.project.customer.Customer;
import com.project.customer.CustomerRepository;
import com.project.customer.Gender;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
//        CustomerService custService = new CustomerService(new CustomerDAOService());
//        CustomerController cusController = new CustomerController(custService);
//        Never do it like this

//        ConfigurableApplicationContext applicationContext =  // to get the beans from app context
//                SpringApplication.run(Main.class, args);

//        printBeans(applicationContext);
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository, PasswordEncoder passwordEncoder){
        return args -> {
            Faker faker = new Faker();
            Random rand = new Random();
            String name = faker.name().fullName();
            int age = rand.nextInt(16, 99);
            Gender gender = (age % 2 == 0) ? Gender.MALE : Gender.FEMALE;

            Customer c1 = new Customer(age,
                    name,
                    name.replaceAll(" ", "_").toLowerCase() + "@gmail.com",
                    gender, passwordEncoder.encode(UUID.randomUUID().toString()));

            customerRepository.save(c1);
        };
    }

    public static void printBeans(ConfigurableApplicationContext ctx){
        String[] beans = ctx.getBeanDefinitionNames(); // returns beans name as a string[]

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
