package com.example.axonmicroservice.aggregate;

import com.example.axonmicroservice.command.ApproveOrderCommand;
import com.example.axonmicroservice.command.CreateOrderCommand;
import com.example.axonmicroservice.command.RejectOrderCommand;
import com.example.axonmicroservice.events.OrderApprovedEvent;
import com.example.axonmicroservice.events.OrderCreatedEvent;
import com.example.axonmicroservice.events.OrderRejectedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String product;
    private BigDecimal price;
    private Integer quantity;
    private OrderStatus status;

    protected OrderAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        if (command.getQuantity() == null || command.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (command.getPrice() == null || command.getPrice().signum() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        AggregateLifecycle.apply(new OrderCreatedEvent(
                command.getOrderId(),
                command.getProduct(),
                command.getPrice(),
                command.getQuantity()
        ));
    }

    @CommandHandler
    public void handle(ApproveOrderCommand command) {
        requireExistingOrder();
        if (OrderStatus.APPROVED.equals(status)) {
            return;
        }
        AggregateLifecycle.apply(new OrderApprovedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handle(RejectOrderCommand command) {
        requireExistingOrder();
        if (OrderStatus.REJECTED.equals(status)) {
            return;
        }
        AggregateLifecycle.apply(new OrderRejectedEvent(command.getOrderId(), command.getReason()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.product = event.getProduct();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
        this.status = OrderStatus.CREATED;
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent event) {
        this.status = OrderStatus.APPROVED;
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent event) {
        this.status = OrderStatus.REJECTED;
    }

    private void requireExistingOrder() {
        if (orderId == null) {
            throw new IllegalStateException("Order does not exist");
        }
    }

    private enum OrderStatus {
        CREATED,
        APPROVED,
        REJECTED
    }
}


