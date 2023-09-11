package com.trendyol.shipment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipmentSizeManager implements IShipmentSizeDeterminer {

    @Override
    public ShipmentSize determineSize(Basket basket) {
        if (basket.isEmpty()) {
            throw new IllegalStateException("Basket is empty");
        }

        Map.Entry<ShipmentSize, Integer> mostFrequentEntry = getMostFrequentSizeAndCount(basket.getProducts());
        ShipmentSize mostFrequentSize = mostFrequentEntry.getKey();

        int threshold = 3;
        if (mostFrequentEntry.getValue() >= threshold) {
            return getNextSize(mostFrequentSize);
        }

        return getLargestSize(basket.getProducts());
    }

    private Map.Entry<ShipmentSize, Integer> getMostFrequentSizeAndCount(List<Product> products) {
        Map<ShipmentSize, Integer> frequencyMap = new HashMap<>();
        for (Product product : products) {
            frequencyMap.put(product.getSize(), frequencyMap.getOrDefault(product.getSize(), 0) + 1);
        }

        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
    }

    private ShipmentSize getNextSize(ShipmentSize size) {
        int nextIndex = Math.min(size.ordinal() + 1, ShipmentSize.X_LARGE.ordinal());
        return ShipmentSize.values()[nextIndex];
    }

    private ShipmentSize getLargestSize(List<Product> products) {
        return products.stream()
                .map(Product::getSize)
                .max(ShipmentSize::compareTo)
                .orElse(ShipmentSize.SMALL);
    }
}
