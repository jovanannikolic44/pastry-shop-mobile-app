package com.example.pastry_shop_mobile_app.models;

public class Item {
    private String id;
    private String name;
    private String description;
    private float price;
    private String[] composition;
    private String type;
    private int imageUrl;

    public Item(String id, String name, String description, float price, String[] composition, String type, int imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.composition = composition;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String[] getComposition() {
        return composition;
    }

    public void setComposition(String[] composition) {
        this.composition = composition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
