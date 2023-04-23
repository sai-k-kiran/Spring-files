package services;

import org.springframework.stereotype.Service;

@Service
public class helloService {
    public String helloName(String name){
        String res = "Hello " + name;
        return res;
    }
}
