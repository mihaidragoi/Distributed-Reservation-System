Distributed Reservation System
A robust, Java-based client-server application designed for handling bookings and reservations across multiple distributed clients simultaneously.

🚀 Main Features
Client-Server Architecture: Built using pure Java Sockets for reliable, low-level network communication between the centralized server and distributed clients.

Concurrent Processing: Utilizes multithreading (ClientHandler) to manage multiple client connections simultaneously, ensuring the server remains responsive.

Thread-Safe Operations: Implements synchronization within the core logic (ReservationService) to prevent race conditions and ensure data consistency during simultaneous booking requests.

Clear Domain Models: Structured object-oriented design using dedicated models for bookings (Reservation) and availability (Slot).

📁 Project Structure
The source code is organized into clear functional components:

Server Infrastructure (SocketServer.java, ClientHandler.java): Listens for incoming client connections and assigns each to a dedicated thread for isolated processing.

Core Business Logic (ReservationService.java): The centralized engine that validates, processes, and stores the booking requests securely.

Client Application (BookingClient.java): The interface allowing users to connect to the server, view available slots, and submit reservations.

Data Entities (Reservation.java, Slot.java): The standard objects representing the system's core data structures.

🛠️ Required Dependencies
To compile and run this project, you will need:

Java Development Kit (JDK) 8 or newer installed on your system.

Command-line terminal or any standard Java IDE (IntelliJ IDEA, Eclipse, VS Code) to compile and execute the .java files.
