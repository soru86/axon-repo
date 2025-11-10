package com.example.axonmicroservice.events;

public class OrderRejectedEvent {

    private final String orderId;
    private final String reason;

    public OrderRejectedEvent(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }
}


