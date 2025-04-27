package com.ey.in.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserRiskService {

    public Double getUserRiskScore(String userId) {
        // stubbing : Generates a number between 1 and 10
        Random random = new Random();
        return (random.nextDouble(10) + 1);

    }
}
