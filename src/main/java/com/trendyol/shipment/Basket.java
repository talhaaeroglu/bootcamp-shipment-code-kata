package com.trendyol.shipment;

import java.util.List;


public class Basket {
    private List<Product> products;
    private final IShipmentSizeDeterminer determiner;

    public Basket(IShipmentSizeDeterminer determiner) {
        this.determiner = determiner;
    }

    public ShipmentSize getShipmentSize() {
        return determiner.determineSize(this);
    }

    public boolean isEmpty() {
        return products == null || products.isEmpty();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}