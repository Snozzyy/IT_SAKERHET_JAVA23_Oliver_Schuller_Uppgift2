package org.example;

import java.util.Scanner;
import org.json.*;

public class Cli {
    private static final Scanner sc = new Scanner(System.in);
    private static boolean isLoggedIn = false;
    private static boolean runMenu = true;
    private static String token;
    private static int choice;

    public static void menu() {
        while (runMenu) {
            if (!isLoggedIn) {
                loggedOutMenu();
            } else {
                loggedInMenu();
            }
        }
    }

    private static void loggedOutMenu() {
        System.out.println("-----------------------------");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your option: ");
        choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                runMenu = false;
        }
    }

    private static void loggedInMenu() {
        System.out.println("-----------------------------");
        System.out.println("1. Encrypt message");
        System.out.println("2. Decrypt messages");
        System.out.println("3. Logout");
        System.out.print("Enter your option: ");
        choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                encryptMessage();
                break;
            case 2:
                getMyMessages();
                break;
            case 3:
                isLoggedIn = false;
                token = null;
                System.out.println("Logged out");
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private static void login() {
        System.out.print("Enter your email: ");
        String email = sc.next();
        System.out.print("Enter you password: ");
        String password = sc.next();

        token = ApiCalls.loginRequest(email, password);
        if (token != null) {
            isLoggedIn = true;
        } else {
            System.out.println("Login failed");
        }
    }

    private static void register() {
        System.out.print("Enter your email: ");
        String email = sc.next();
        sc.nextLine();
        System.out.print("Enter you password: ");
        String password = sc.next();
        sc.nextLine();

        ApiCalls.registerRequest(email, password);
    }

    private static void encryptMessage() {
        System.out.print("Enter your message: ");
        String message = sc.nextLine();

        message = message.replace(" ", "%20");

        String key = ApiCalls.encryptMessage(token, message);
        System.out.println("----------IMPORTANT----------");
        System.out.println("Your key to decrypt this message is " + key);
        System.out.println("Save the key in a secure spot to decrypt your message later");
    }

    private static void getMyMessages() {
        System.out.println("-----------------------------");
        String jsonResponse = ApiCalls.getMessages(token);
        JSONArray jsonArray = new JSONArray(jsonResponse);

        if (!jsonArray.isEmpty()) {
            for (int i = 0; i < jsonArray.length(); i++) {
                System.out.println(i+1 + ". " + jsonArray.getString(i));
            }

            System.out.print("Choose the message you want to decrypt: ");
            choice = sc.nextInt();

            String message = jsonArray.getString(choice-1);

            System.out.print("Write you decryption key: ");
            String key = sc.next();
            String decryptedMessage = ApiCalls.decryptMessage(key, message);

            decryptedMessage = decryptedMessage.replace( "%20", " ");

            System.out.println("#############################");
            System.out.println("Decrypted message: " + decryptedMessage);
            System.out.println("#############################");
        } else {
            System.out.println("No messages found");
        }
    }
}
