package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ProductRepository;

@Service
public class ProductDeliveryService {
    @Autowired
    ProductRepository prepo;

    public void addSome(){
        prepo.add();
    }

    @Transactional
    public void addOneProduct(){
        prepo.addProduct("Phone");
        throw new RuntimeException("test ecxeption");
    }
}
