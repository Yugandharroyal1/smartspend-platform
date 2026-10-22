package com.yugandhar.smartspend.controller;
import com.yugandhar.smartspend.model.*;
import com.yugandhar.smartspend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired ProductRepository productRepo;
    @Autowired PriceRepository priceRepo;
    @Autowired StoreRepository storeRepo;

    @GetMapping
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String query) {
        return productRepo.findByNameContainingIgnoreCase(query);
    }

    @GetMapping("/{id}/prices")
    public List<Price> getPrices(@PathVariable Long id) {
        return priceRepo.findByProductId(id);
    }

    @GetMapping("/compare")
    public Map<String, Object> compare(@RequestParam String query) {
        List<Product> products = productRepo.findByNameContainingIgnoreCase(query);
        Map<String, Object> result = new HashMap<>();
        if (products.isEmpty()) {
            result.put("message", "Product not found");
            return result;
        }
        Product product = products.get(0);
        List<Price> prices = priceRepo.findByProductId(product.getId());
        Price cheapest = null;
        for (Price p : prices) {
            double effectivePrice = p.getSalePrice() != null ? p.getSalePrice() : p.getRegularPrice();
            if (cheapest == null) {
                cheapest = p;
            } else {
                double cheapestPrice = cheapest.getSalePrice() != null ? cheapest.getSalePrice() : cheapest.getRegularPrice();
                if (effectivePrice < cheapestPrice) cheapest = p;
            }
        }
        result.put("product", product.getName());
        result.put("prices", prices);
        result.put("cheapestStore", cheapest != null ? cheapest.getStore().getName() : "N/A");
        result.put("cheapestPrice", cheapest != null ? (cheapest.getSalePrice() != null ? cheapest.getSalePrice() : cheapest.getRegularPrice()) : 0);
        return result;
    }

    @GetMapping("/stores")
    public List<Store> getStores() {
        return storeRepo.findAll();
    }
}