package com.trendyol.shipment;

public interface IShipmentSizeDeterminer {
    ShipmentSize determineSize(Basket basket);
}