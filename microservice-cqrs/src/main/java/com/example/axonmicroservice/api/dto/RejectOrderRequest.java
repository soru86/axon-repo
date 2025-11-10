package com.example.axonmicroservice.api.dto;

import jakarta.validation.constraints.NotBlank;

public class RejectOrderRequest {

    @NotBlank
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}


