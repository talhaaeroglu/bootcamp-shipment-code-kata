package com.trendyol.shipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipmentSizeManager {
    private final int threshold = 3;
    private Basket basket;

    public ShipmentSizeManager(Basket basket) {
        this.basket = basket;
    }

    public ShipmentSize determineSize() {
        if (basket.isEmpty()) {
            throw new IllegalStateException("Basket is empty");
        }

        ShipmentSize mostFrequentSize = getMostFrequentSize();
        if (hasSufficientFrequency(mostFrequentSize)) {
            return getNextSize(mostFrequentSize);
        }

        return getLargestSize();
    }

    private ShipmentSize getMostFrequentSize() {
        List<ShipmentSize> sizes = new ArrayList<>();
        for (Product product : basket.getProducts()) {
            sizes.add(product.getSize());
        }

        Map<ShipmentSize, Integer> frequencyMap = new HashMap<>();
        for (ShipmentSize size : sizes) {
            frequencyMap.put(size, frequencyMap.getOrDefault(size, 0) + 1);
        }

        ShipmentSize mostFrequent = ShipmentSize.SMALL;
        int maxCount = 0;
        for (Map.Entry<ShipmentSize, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }

        return mostFrequent;
    }

    private boolean hasSufficientFrequency(ShipmentSize size) {
        int count = 0;
        for (Product product : basket.getProducts()) {
            if (product.getSize() == size) {
                count++;
            }
        }
        return count >= threshold;
    }

    private ShipmentSize getNextSize(ShipmentSize size) {
        int nextIndex = Math.min(size.ordinal() + 1, ShipmentSize.X_LARGE.ordinal());
        return ShipmentSize.values()[nextIndex];
    }

    private ShipmentSize getLargestSize() {
        ShipmentSize largest = ShipmentSize.SMALL;
        for (Product product : basket.getProducts()) {
            if (product.getSize().ordinal() > largest.ordinal()) {
                largest = product.getSize();
            }
        }
        return largest;
    }
}
