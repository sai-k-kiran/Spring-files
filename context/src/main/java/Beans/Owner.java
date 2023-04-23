package Beans;

import org.springframework.stereotype.Component;

@Component
public class Owner {

    private Cat cat;

    public Cat getCat(){
        return cat;
    }

    public void setCat(Cat cat){
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "Cat name='" + cat.getName() + '\'' +
                '}';
    }
}
