package repository;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    public void add(){ System.out.println("New product added");}
}
