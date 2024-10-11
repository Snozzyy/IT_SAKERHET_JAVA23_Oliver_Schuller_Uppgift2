package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONObject;

public class ApiCalls {

    static String baseUrl = "http://localhost:8080";

    private static String sendRequest(String url, String method) {
        StringBuilder response = new StringBuilder();

        try {
            URL requestUrl = URI.create(url).toURL();
            HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
            con.setRequestMethod(method);
            int responseCode = con.getResponseCode();

            if (responseCode >= 200 && responseCode < 300) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.err.println("Request failed with response code: " + responseCode + "\n");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return response.toString();
    }

    public static String loginRequest(String email, String password) {
        String requestUrl = baseUrl + String.format("/login?email=%s&password=%s", email, password);
        String response = sendRequest(requestUrl, "GET");

        if (!response.equals("No user found")) {
            return response;
        } else {
            return null;
        }
    }

    public static void registerRequest(String email, String password) {
        String requestUrl = baseUrl + String.format("/register?email=%s&password=%s", email, password);
        sendRequest(requestUrl, "POST");
    }

    public static String encryptMessage(String token, String message) {
        String requestUrl = baseUrl + String.format("/message/write?message=%s&token=%s", message, token);
        return sendRequest(requestUrl, "POST");
    }

    public static String getMessages(String token) {
        String requestUrl = baseUrl + String.format("/message/my-messages?token=%s", token);
        return sendRequest(requestUrl, "GET");
    }

    public static String decryptMessage(String key, String message) {
        String requestUrl = baseUrl + String.format("/message/decrypt?key=%s&message=%s", key, message);
        return sendRequest(requestUrl, "GET");
    }
}
