import java.io.*;
import java.net.*;

public class ChatClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("Server IP address", 5000);
            System.out.println("Connected to server!");

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true);

            BufferedReader console = new BufferedReader(
                    new InputStreamReader(System.in));

            // Thread for receiving messages
            new Thread(() -> {
                String msg;
                try {
                    while ((msg = input.readLine()) != null) {
                        System.out.println("Server: " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            }).start();

            // Main thread for sending messages
            String msg;
            while ((msg = console.readLine()) != null) {
                output.println(msg);
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}