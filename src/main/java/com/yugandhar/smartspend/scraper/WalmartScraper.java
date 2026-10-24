package com.yugandhar.smartspend.scraper;
import com.yugandhar.smartspend.model.*;
import com.yugandhar.smartspend.repository.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public class WalmartScraper {
    @Autowired ProductRepository productRepo;
    @Autowired StoreRepository storeRepo;
    @Autowired PriceRepository priceRepo;
    @Scheduled(fixedRate=3600000)
    public void scrapeWalmartPrices() {
        try {
            Store walmart = storeRepo.findByName("Walmart")
                .orElseGet(() -> storeRepo.save(new Store(null,"Walmart","Superstore","walmart.com")));
            Document doc = Jsoup.connect("https://www.walmart.com/browse/food/976759")
                .userAgent("Mozilla/5.0").timeout(10000).get();
            Elements items = doc.select("[data-item-id]");
            for (Element item : items) {
                String name = item.select("[data-automation-id=product-title]").text();
                String priceText = item.select("[data-automation-id=product-price]").text()
                    .replaceAll("[^0-9.]","");
                if (!name.isEmpty() && !priceText.isEmpty()) {
                    double price = Double.parseDouble(priceText);
                    Product product = productRepo.findByNameContainingIgnoreCase(name)
                        .stream().findFirst()
                        .orElseGet(() -> productRepo.save(new Product(null,name,"Walmart","Grocery","each")));
                    priceRepo.save(new Price(null,product,walmart,price,null,false,LocalDateTime.now()));
                }
            }
            System.out.println("Walmart scrape done!");
        } catch (Exception e) {
            System.out.println("Walmart error: " + e.getMessage());
        }
    }
}