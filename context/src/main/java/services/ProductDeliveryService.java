package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProductRepository;

@Service
public class ProductDeliveryService {
    @Autowired
    ProductRepository prepo;

    public void addProduct(){
        prepo.add();
    }
}
