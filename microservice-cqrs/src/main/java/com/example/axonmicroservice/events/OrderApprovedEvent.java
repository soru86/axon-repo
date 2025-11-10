package com.example.axonmicroservice.events;

public class OrderApprovedEvent {

    private final String orderId;

    public OrderApprovedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}


