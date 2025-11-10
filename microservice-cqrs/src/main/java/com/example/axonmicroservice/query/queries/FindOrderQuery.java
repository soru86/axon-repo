package com.example.axonmicroservice.query.queries;

public class FindOrderQuery {

    private final String orderId;

    public FindOrderQuery(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}


