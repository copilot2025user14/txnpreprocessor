package com.ey.in;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableCaching
public class TxnpreprocessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxnpreprocessorApplication.class, args);
	}

}
