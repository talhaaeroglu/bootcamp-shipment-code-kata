package com.trendyol.shipment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        ShipmentSizeManager manager = new ShipmentSizeManager(this);
        return manager.determineSize();
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
