package com.project.context;

import Beans.Cat;
import Beans.Owner;
import config.ProjectConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.ProductDeliveryService;

@SpringBootApplication
public class ContextApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(ProjectConfig.class);

		ProductDeliveryService service = context.getBean(ProductDeliveryService.class);
//		service.addProduct();
//
//		Owner man = context.getBean(Owner.class);
//
//		Cat cat = context.getBean("cat", Cat.class);
//
//		//cat.setName("Hammy");
//		System.out.println(man);
//		System.out.println(cat);

		service.addOneProduct();

	}

}
