package com.klear.tradesettlement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Distributed Execution, Clearing, and Settlement",version = "1.1.0-SNAPSHOT"))
public class KlearTradeSettlementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(KlearTradeSettlementSystemApplication.class, args);
	}

}
