package com.yugandhar.smartspend.service;
import com.yugandhar.smartspend.model.*;
import com.yugandhar.smartspend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.time.LocalDateTime;
import java.util.*;
@Service
public class KrogerApiService {
    @Value("${kroger.client.id}") private String clientId;
    @Value("${kroger.client.secret}") private String clientSecret;
    @Value("${kroger.base.url}") private String baseUrl;
    @Autowired ProductRepository productRepo;
    @Autowired PriceRepository priceRepo;
    @Autowired StoreRepository storeRepo;
    public String getAccessToken() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String encoded = Base64.getEncoder().encodeToString((clientId+":"+clientSecret).getBytes());
        headers.set("Authorization", "Basic " + encoded);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("scope", "product.compact");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = rest.postForEntity("https://api.kroger.com/v1/connect/oauth2/token", request, Map.class);
        return (String) response.getBody().get("access_token");
    }
    @Scheduled(fixedRate = 3600000)
    public void fetchKrogerProducts() {
        try {
            String token = getAccessToken();
            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            Store kroger = storeRepo.findByName("Kroger").orElse(storeRepo.save(new Store(null,"Kroger","Supermarket","kroger.com")));
            String[] terms = {"eggs","milk","bread","chicken","beer","chips","soda","rice","butter","water"};
            for (String term : terms) {
                String url = baseUrl + "/products?filter.term=" + term + "&filter.limit=10";
                ResponseEntity<Map> response = rest.exchange(url, HttpMethod.GET, entity, Map.class);
                List<Map> items = (List<Map>) response.getBody().get("data");
                if (items != null) {
                    for (Map item : items) {
                        String name = (String) item.get("description");
                        List<Map> prices = (List<Map>) item.get("items");
                        if (name != null && prices != null && !prices.isEmpty()) {
                            Map priceInfo = (Map) prices.get(0).get("price");
                            if (priceInfo != null) {
                                Double regular = priceInfo.get("regular") != null ? Double.valueOf(priceInfo.get("regular").toString()) : null;
                                Double promo = priceInfo.get("promo") != null ? Double.valueOf(priceInfo.get("promo").toString()) : null;
                                Product product = productRepo.findByNameContainingIgnoreCase(name).stream().findFirst().orElse(productRepo.save(new Product(null,name,"Kroger",term,"each")));
                                priceRepo.save(new Price(null,product,kroger,regular,promo,promo!=null&&promo>0,LocalDateTime.now()));
                            }
                        }
                    }
                }
            }
            System.out.println("Kroger products fetched!");
        } catch (Exception e) {
            System.out.println("Kroger error: " + e.getMessage());
        }
    }
}