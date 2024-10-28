package com.klear.tradesettlement.controller;

import com.klear.tradesettlement.model.Client;
import com.klear.tradesettlement.service.impl.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class ClientController {
    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/clients")
    @Operation(summary = "Create a new client", description = "Creates a new client and returns the created client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid client data provided")
    })
    public Mono<ResponseEntity<Void>> createClient(@RequestBody Client client) {
        logger.info("Creating client: {}", client);
        return clientService.createClient(client)
            .map(savedClient -> ResponseEntity.ok(savedClient))
                .doOnError(error -> logger.error("Error creating client: {}", error.getMessage()))
                .onErrorReturn(ResponseEntity.badRequest().build());

    }
}
