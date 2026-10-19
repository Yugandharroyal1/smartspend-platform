package com.yugandhar.smartspend.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="prices")
public class Price {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name="product_id")
    private Product product;
    @ManyToOne @JoinColumn(name="store_id")
    private Store store;
    private Double regularPrice;
    private Double salePrice;
    private Boolean isOnSale;
    private LocalDateTime recordedAt;
    public Price() {}
    public Price(Long id, Product product, Store store, Double regularPrice, Double salePrice, Boolean isOnSale, LocalDateTime recordedAt) {
        this.id=id; this.product=product; this.store=store;
        this.regularPrice=regularPrice; this.salePrice=salePrice;
        this.isOnSale=isOnSale; this.recordedAt=recordedAt;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id=id; }
    public Product getProduct() { return product; }
    public void setProduct(Product p) { this.product=p; }
    public Store getStore() { return store; }
    public void setStore(Store s) { this.store=s; }
    public Double getRegularPrice() { return regularPrice; }
    public void setRegularPrice(Double p) { this.regularPrice=p; }
    public Double getSalePrice() { return salePrice; }
    public void setSalePrice(Double p) { this.salePrice=p; }
    public Boolean getIsOnSale() { return isOnSale; }
    public void setIsOnSale(Boolean b) { this.isOnSale=b; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime t) { this.recordedAt=t; }
}