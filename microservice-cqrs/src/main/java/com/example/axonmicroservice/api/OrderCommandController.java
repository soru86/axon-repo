package com.example.axonmicroservice.api;

import com.example.axonmicroservice.api.dto.CreateOrderRequest;
import com.example.axonmicroservice.api.dto.RejectOrderRequest;
import com.example.axonmicroservice.command.ApproveOrderCommand;
import com.example.axonmicroservice.command.CreateOrderCommand;
import com.example.axonmicroservice.command.RejectOrderCommand;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderCommandController {

    private final CommandGateway commandGateway;

    public OrderCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        CreateOrderCommand command = new CreateOrderCommand(
                request.getOrderId(),
                request.getProduct(),
                request.getPrice(),
                request.getQuantity()
        );
        return commandGateway.send(command)
                .thenApply(result -> ResponseEntity.ok("Order created with id " + request.getOrderId()));
    }

    @PostMapping("/{orderId}/approve")
    public CompletableFuture<ResponseEntity<String>> approveOrder(@PathVariable String orderId) {
        return commandGateway.send(new ApproveOrderCommand(orderId))
                .thenApply(result -> ResponseEntity.ok("Order approved with id " + orderId));
    }

    @PostMapping("/{orderId}/reject")
    public CompletableFuture<ResponseEntity<String>> rejectOrder(@PathVariable String orderId,
                                                                 @RequestBody @Valid RejectOrderRequest request) {
        return commandGateway.send(new RejectOrderCommand(orderId, request.getReason()))
                .thenApply(result -> ResponseEntity.ok("Order rejected with id " + orderId));
    }
}


