package com.example.axonmicroservice.query.queries;

import com.example.axonmicroservice.query.model.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSummaryRepository extends JpaRepository<OrderSummary, String> {
}


