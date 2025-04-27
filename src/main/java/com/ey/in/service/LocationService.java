package com.ey.in.service;

import org.springframework.stereotype.Service;

@Service
public class LocationService {

    public String getLocation(String merchantId) {
        // stubbing : Generates a random location
        String[] locations = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
        int randomIndex = (int) (Math.random() * locations.length);
        return locations[randomIndex];
    }
}
