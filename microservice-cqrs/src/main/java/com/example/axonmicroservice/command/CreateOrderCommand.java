package com.example.axonmicroservice.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
    private final String product;
    private final BigDecimal price;
    private final Integer quantity;

    public CreateOrderCommand(String orderId, String product, BigDecimal price, Integer quantity) {
        this.orderId = orderId;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}


