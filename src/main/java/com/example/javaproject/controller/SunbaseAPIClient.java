package com.example.javaproject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SunbaseAPIClient {

    private static final String AUTH_URL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    private static final String BASE_URL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

    private String bearerToken;

    // Helper method to perform the authentication request and retrieve the bearer token
    public void authenticate(String username, String password) throws IOException {
        System.out.println("Authenticating...");
        URL url = new URL(AUTH_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String body = "{\"login_id\":\"" + username + "\",\"password\":\"" + password + "\"}";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = body.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                bearerToken = response.toString();
                System.out.println(bearerToken);
            }
        }
        else {
            System.out.println("Authentication failed");
        }

        connection.disconnect();
    }

    // Helper method to make API calls with authorization header (bearer token)
    private String makeAPICall(String method, String path, String parameters, String body) throws IOException {
        URL url = new URL(BASE_URL + path + parameters);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + bearerToken);

        if (body != null && !body.isEmpty()) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }

        // int responseCode = connection.getResponseCode();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (IOException e) {
            return null;
        } finally {
            connection.disconnect();
        }
    }

    public String createCustomer(String firstName, String lastName, String street, String address, String city, String state, String email, String phone) throws IOException {
        String path = "";
        String parameters = "?cmd=create";
        String body = "{\"first_name\":\"" + firstName + "\",\"last_name\":\"" + lastName + "\",\"street\":\"" + street + "\",\"address\":\"" + address + "\",\"city\":\"" + city + "\",\"state\":\"" + state + "\",\"email\":\"" + email + "\",\"phone\":\"" + phone + "\"}";

        return makeAPICall("POST", path, parameters, body);
    }

    public String getCustomerList() throws IOException {
        String path = "";
        String parameters = "?cmd=get_customer_list";

        return makeAPICall("GET", path, parameters, null);
    }

    public String deleteCustomer(String uuid) throws IOException {
        String path = "";
        String parameters = "?cmd=delete&uuid=" + uuid;

        return makeAPICall("POST", path, parameters, null);
    }

    public String updateCustomer(String uuid, String firstName, String lastName, String street, String address, String city, String state, String email, String phone) throws IOException {
        String path = "";
        String parameters = "?cmd=update&uuid=" + uuid;
        String body = "{\"first_name\":\"" + firstName + "\",\"last_name\":\"" + lastName + "\",\"street\":\"" + street + "\",\"address\":\"" + address + "\",\"city\":\"" + city + "\",\"state\":\"" + state + "\",\"email\":\"" + email + "\",\"phone\":\"" + phone + "\"}";

        return makeAPICall("POST", path, parameters, body);
    }
}
