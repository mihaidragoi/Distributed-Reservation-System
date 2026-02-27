🌐 System Architecture
The project is built on the TCP/IP stack, focusing on reliable, ordered delivery of data between a central hub and various entry points.

The Server: Acts as a continuous listener, spawning independent threads to handle each client's unique lifecycle.

The Client: A focused interface that interacts directly with the server's shared reservation state.

Thread Management: Every connection is isolated into its own execution context to ensure the server never hangs while waiting for a single user's input.

🔥 Technical Highlights
🧵 Thread-per-Connection: Uses Java's Thread class to ensure the server remains responsive even when dozens of clients are connected.

📡 Stream-Based I/O: Utilizes BufferedReader and PrintWriter for efficient, line-based data exchange.

🛡️ Error Resilience: Gracefully handles socket timeouts, unexpected client disconnects, and port conflicts.
