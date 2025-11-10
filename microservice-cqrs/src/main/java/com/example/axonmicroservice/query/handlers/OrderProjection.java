package com.example.axonmicroservice.query.handlers;

import com.example.axonmicroservice.events.OrderApprovedEvent;
import com.example.axonmicroservice.events.OrderCreatedEvent;
import com.example.axonmicroservice.events.OrderRejectedEvent;
import com.example.axonmicroservice.query.model.OrderSummary;
import com.example.axonmicroservice.query.queries.FindAllOrdersQuery;
import com.example.axonmicroservice.query.queries.FindOrderQuery;
import com.example.axonmicroservice.query.queries.OrderSummaryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class OrderProjection {

    private final OrderSummaryRepository repository;

    public OrderProjection(OrderSummaryRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderSummary summary = new OrderSummary(
                event.getOrderId(),
                event.getProduct(),
                event.getPrice(),
                event.getQuantity(),
                "CREATED"
        );
        repository.save(summary);
    }

    @EventHandler
    public void on(OrderApprovedEvent event) {
        repository.findById(event.getOrderId()).ifPresent(summary -> {
            summary.setStatus("APPROVED");
            summary.setRejectionReason(null);
            repository.save(summary);
        });
    }

    @EventHandler
    public void on(OrderRejectedEvent event) {
        repository.findById(event.getOrderId()).ifPresent(summary -> {
            summary.setStatus("REJECTED");
            summary.setRejectionReason(event.getReason());
            repository.save(summary);
        });
    }

    @QueryHandler
    @Transactional(readOnly = true)
    public List<OrderSummary> handle(FindAllOrdersQuery query) {
        return repository.findAll();
    }

    @QueryHandler
    @Transactional(readOnly = true)
    public OrderSummary handle(FindOrderQuery query) {
        return repository.findById(query.getOrderId()).orElse(null);
    }
}


