package com.klear.tradesettlement.controller;

import com.klear.tradesettlement.model.TradeOrder;
import com.klear.tradesettlement.service.TradeOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class TradeOrderController {
    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private KafkaTemplate<String, TradeOrder> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderController.class);

    @PostMapping("/orders")
    @Operation(summary = "Create a new trade order",
            description = "Creates a new trade order and returns the created order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = TradeOrder.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input",
                    content = @Content)
    })
    public Mono<ResponseEntity<TradeOrder>> createOrder(@RequestBody TradeOrder tradeOrder) {

        logger.info("Received request to create order: {}", tradeOrder);

        return tradeOrderService.createOrder(tradeOrder)
                .doOnSuccess(savedOrder -> {
                    logger.info("Order created successfully: {}", savedOrder);
                    kafkaTemplate.send("trade-events", savedOrder);
                })
                .doOnError(error -> logger.error("Error creating order: {}", error.getMessage()))
                .map(savedOrder -> ResponseEntity.status(201)
                        .header("X-Message", "Trade Order created successfully") // Add your message in headers
                        .body(savedOrder))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/order/status/{id}")
    @Operation(summary = "Get order status by ID",
            description = "Retrieves the current status of the order identified by the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public Mono<ResponseEntity<String>> getStatus(@PathVariable("id") String id) {
        return tradeOrderService.getOrderStatus(id)
                .doOnSuccess(status -> System.out.println(status))
                .doOnError(error -> System.out.println("Failed: " + error.getMessage()))
                .map(status -> ResponseEntity.ok(status))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
