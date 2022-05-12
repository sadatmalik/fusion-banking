package com.sadatmalik.fusionbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

/**
 * Main Spring bootstrap application. Launches Fusion Banking.
 *
 * The @EnableBinding annotation tells Spring Cloud Stream that we want to bind the service to a
 * message broker. The use of Source.class in @EnableBinding tells Spring Cloud Stream that this
 * service will communicate with the message broker via a set of channels defined in the Source
 * class.
 *
 * In Spring Cloud Stream, channels sit above a message queue. Spring Cloud Stream has a default
 * set of channels that can be configured to speak to a message broker.
 *
 * @author sadatmalik
 */
@SpringBootApplication
@EnableBinding(Source.class)
public class FusionBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FusionBankingApplication.class, args);
	}

}
