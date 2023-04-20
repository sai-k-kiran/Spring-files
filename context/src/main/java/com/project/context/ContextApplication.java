package com.project.context;

import Beans.Cat;
import Beans.Owner;
import config.ProjectConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.ProductDeliveryService;

@SpringBootApplication
public class ContextApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(ProjectConfig.class);

		ProductDeliveryService service = context.getBean(ProductDeliveryService.class);
		Owner man = context.getBean(Owner.class);
		Cat cat = context.getBean(Cat.class);

		service.addProduct();

		cat.setName("Tom");
		System.out.println(man);
		System.out.println(cat);
	}

}
