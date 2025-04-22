package com.ey.in;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class TxnpreprocessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxnpreprocessorApplication.class, args);
	}

}
