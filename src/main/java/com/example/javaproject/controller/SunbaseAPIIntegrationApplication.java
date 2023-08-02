package com.example.javaproject.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@SpringBootApplication
@RestController
public class SunbaseAPIIntegrationApplication {

    private SunbaseAPIClient sunbaseAPIClient = new SunbaseAPIClient();

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            sunbaseAPIClient.authenticate(username, password);
            return "Authentication successful";
        } catch (IOException e) {
            return "Authentication failed";
        }
    }

    @PostMapping("/customers")
    public String createCustomer(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                 @RequestParam("street") String street, @RequestParam("address") String address,
                                 @RequestParam("city") String city, @RequestParam("state") String state,
                                 @RequestParam("email") String email, @RequestParam("phone") String phone) {
        try {
            String response = sunbaseAPIClient.createCustomer(firstName, lastName, street, address, city, state, email, phone);
            return response;
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/customers")
    public String getCustomerList() {
        try {
            String response = sunbaseAPIClient.getCustomerList();
            return response;
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/customers/{uuid}")
    public String updateCustomer(@PathVariable("uuid") String uuid, @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName, @RequestParam("street") String street,
                                 @RequestParam("address") String address, @RequestParam("city") String city,
                                 @RequestParam("state") String state, @RequestParam("email") String email,
                                 @RequestParam("phone") String phone) {
        try {
            String response = sunbaseAPIClient.updateCustomer(uuid, firstName, lastName, street, address, city, state, email, phone);
            return response;
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    @DeleteMapping("/customers/{uuid}")
    public String deleteCustomer(@PathVariable("uuid") String uuid) {
        try {
            String response = sunbaseAPIClient.deleteCustomer(uuid);
            return response;
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SunbaseAPIIntegrationApplication.class, args);
    }
}

