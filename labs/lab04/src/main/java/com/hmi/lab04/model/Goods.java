package com.hmi.lab04.model;

/**
 * 商品实体类。
 * <p>购物车 Cookie 中只保存商品编号（id），展示时再据此查出商品名称与价格。</p>
 */
public class Goods {

    private String id;
    private String name;
    private double price;

    public Goods() {
    }

    public Goods(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
