package com.example.axonmicroservice.api;

import com.example.axonmicroservice.query.model.OrderSummary;
import com.example.axonmicroservice.query.queries.FindAllOrdersQuery;
import com.example.axonmicroservice.query.queries.FindOrderQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
public class OrderQueryController {

    private final QueryGateway queryGateway;

    public OrderQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<OrderSummary>>> getOrders() {
        return queryGateway.query(new FindAllOrdersQuery(),
                        ResponseTypes.multipleInstancesOf(OrderSummary.class))
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{orderId}")
    public CompletableFuture<ResponseEntity<OrderSummary>> getOrder(@PathVariable String orderId) {
        return queryGateway.query(new FindOrderQuery(orderId),
                        ResponseTypes.instanceOf(OrderSummary.class))
                .thenApply(summary -> summary != null
                        ? ResponseEntity.ok(summary)
                        : ResponseEntity.notFound().build());
    }
}


