package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
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

    //---------------lesson 42-----------------------------------------------

    class Customer{
        private int id, age;
        private String name, email;

        public Customer(){}

        public Customer(int id, int age, String name, String email) {
            this.id = id;
            this.age = age;
            this.name = name;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", age=" + age +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}

// @RequestParam("name") is for the URL params.
// ex: localhost:8080/json?name="Saik", here name="Saik" is a param passed to greet1() above
// then the person name would be "Saik", else person name = "Generic"
