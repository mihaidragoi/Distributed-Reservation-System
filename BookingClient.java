package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BookingClient {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost",8080);) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner input = new Scanner(System.in);
            System.out.println("Client started. Type commands (MY, RESERVE, CANCEL, EXIT):");

            new Thread(() -> {
                String serverResponse;
                try {
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println("Server: " + serverResponse);
                    }
                } catch (IOException e) {
                   if(!socket.isClosed()){
                       System.err.println("Connection to server lost: " + e.getMessage());
                   }
                }
            }).start();


            while (true) {
                String userCommand = input.nextLine();
                out.println(userCommand);
                if (userCommand.trim().equalsIgnoreCase("EXIT")) {
                    break;
                }
            }

        }
        catch (IOException e) {
            System.err.println("Could not connect to the server."+ e.getMessage());
        }
    }
}
