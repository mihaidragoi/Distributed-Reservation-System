package com.handler;

import com.service.ReservationService;
import jakarta.enterprise.context.control.ActivateRequestContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket socket;
    private ReservationService service;

    public ClientHandler(Socket socket, ReservationService service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    @ActivateRequestContext
    public void run() {

        int noMagicNumber = 1000;
        String clientToken = "ClientToken-" + System.currentTimeMillis()%noMagicNumber;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(),true))
        {
            out.println("Welcome to the Reservation Service! Your token is: " + clientToken);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String command = inputLine.trim().toUpperCase().trim();
                if(command.equals("EXIT"))
                {
                    out.println("Goodbye!");
                    break;
                }

                switch (command) {
                    case "LIST":
                        out.println("All Reservations:");
                        if(service.getAvailableSlots().isEmpty()){
                            out.println("No slots found.");
                        }
                        else
                            service.getAvailableSlots().forEach(reservation -> out.println(reservation.toString()));
                        break;
                    case "MY":
                        out.println("Your Reservations:");
                        if(service.getByClient(clientToken).isEmpty()){
                            out.println("You have no reservations.");
                        }
                        else
                            service.getByClient(clientToken).forEach(reservation -> out.println(reservation.toString()));
                        break;
                    case "RESERVE":
                        out.println("Enter Reservation Date (YYYY-MM-DD) to book:");
                        String reservationDate = in.readLine();
                        String customerName = clientToken;
                        Long slotId = null;
                        out.println("Enter Slot ID to reserve:");
                        try {
                            String slotIdStr = in.readLine();
                            slotId = Long.parseLong(slotIdStr);
                        } catch (NumberFormatException e) {
                            out.println("Invalid Slot ID format.");
                            break;
                        }
                        String response = service.reserveSlot(slotId,customerName, reservationDate);
                        out.println(response);
                        break;
                    case "CANCEL":
                        if(service.getByClient(clientToken).isEmpty()){
                            out.println("You have no reservations to cancel.");
                            break;
                        }
                        out.println("Enter Reservation ID to cancel:");
                        String idStr = in.readLine();
                        try {
                            Long resId = Long.parseLong(idStr);
                            if(!service.getByClient(clientToken).stream().anyMatch(r -> r.getId().equals(resId))) {
                                out.println("You can only cancel your own reservations.");
                                break;
                            }
                            service.cancelReservation(resId);
                            out.println("Reservation with ID " + resId + " cancelled.");
                        } catch (NumberFormatException e) {
                            out.println("Invalid ID format.");
                        }
                        break;
                    default:
                        out.println("Unknown command. Available commands: LIST, MY, RESERVE, CANCEL, EXIT");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
