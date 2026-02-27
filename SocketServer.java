package com.server;

import com.handler.ClientHandler;
import com.service.ReservationService;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.net.Socket;

@ApplicationScoped
public class SocketServer {

    @Inject
    ReservationService reservationService;

    public void onStart(@Observes StartupEvent ev) {
        reservationService.initData();
        new Thread(this::runServer).start();
    }

    private void runServer() {
        int port = 8080;
        try (java.net.ServerSocket serverSocket = new java.net.ServerSocket(port)) {
            System.out.println("Socket server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostName());

                ClientHandler handler = new ClientHandler(clientSocket, reservationService);
                new Thread(handler).start();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

