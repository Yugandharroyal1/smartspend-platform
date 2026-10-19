package com.yugandhar.smartspend.model;
import jakarta.persistence.*;
@Entity
@Table(name="products")
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String category;
    private String unit;
    public Product() {}
    public Product(Long id, String name, String brand, String category, String unit) {
        this.id=id; this.name=name; this.brand=brand; this.category=category; this.unit=unit;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id=id; }
    public String getName() { return name; }
    public void setName(String name) { this.name=name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand=brand; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category=category; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit=unit; }
}