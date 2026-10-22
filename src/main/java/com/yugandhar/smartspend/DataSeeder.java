package com.yugandhar.smartspend;
import com.yugandhar.smartspend.model.*;
import com.yugandhar.smartspend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
@Component
public class DataSeeder implements CommandLineRunner {
        @Autowired StoreRepository storeRepo;
        @Autowired ProductRepository productRepo;
        @Autowired PriceRepository priceRepo;
        @Override
        public void run(String... args) throws Exception {
                if (storeRepo.count() > 0) return;
                Store w = storeRepo.save(new Store(null,"Walmart","Superstore","walmart.com"));
                Store t = storeRepo.save(new Store(null,"Target","Superstore","target.com"));
                Store c = storeRepo.save(new Store(null,"Costco","Warehouse","costco.com"));
                Store k = storeRepo.save(new Store(null,"Kroger","Supermarket","kroger.com"));
                Store p = storeRepo.save(new Store(null,"Publix","Supermarket","publix.com"));
                Product eggs = productRepo.save(new Product(null,"Eggs 12 Count","Generic","Dairy","12 count"));
                Product milk = productRepo.save(new Product(null,"Whole Milk Gallon","Generic","Dairy","1 gallon"));
                Product chicken = productRepo.save(new Product(null,"Chicken Breast","Generic","Meat","1 lb"));
                Product beer = productRepo.save(new Product(null,"Heineken 12 Pack","Heineken","Beer","12 pack"));
                Product bread = productRepo.save(new Product(null,"Wonder Bread","Wonder","Bakery","20oz"));
                priceRepo.save(new Price(null,eggs,w,2.97,null,false,LocalDateTime.now()));
                priceRepo.save(new Price(null,eggs,t,3.49,2.99,true,LocalDateTime.now()));
                priceRepo.save(new Price(null,eggs,c,8.99,null,false,LocalDateTime.now()));
                priceRepo.save(new Price(null,eggs,k,3.29,2.99,true,LocalDateTime.now()));
                priceRepo.save(new Price(null,milk,w,3.47,null,false,LocalDateTime.now()));
                priceRepo.save(new Price(null,milk,t,3.99,null,false,LocalDateTime.now()));
                priceRepo.save(new Price(null,beer,w,14.98,null,false,LocalDateTime.now()));
                priceRepo.save(new Price(null,beer,t,15.99,13.99,true,LocalDateTime.now()));
                priceRepo.save(new Price(null,chicken,w,2.97,null,false,LocalDateTime.now()));
                priceRepo.save(new Price(null,bread,w,2.97,null,false,LocalDateTime.now()));
                System.out.println("SmartSpend Seeded!");
        }
}