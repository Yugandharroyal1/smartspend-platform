package com.yugandhar.smartspend.model;
import jakarta.persistence.*;
@Entity
@Table(name="stores")
public class Store {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String website;
    public Store() {}
    public Store(Long id, String name, String type, String website) {
        this.id=id; this.name=name; this.type=type; this.website=website;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id=id; }
    public String getName() { return name; }
    public void setName(String name) { this.name=name; }
    public String getType() { return type; }
    public void setType(String type) { this.type=type; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website=website; }
}