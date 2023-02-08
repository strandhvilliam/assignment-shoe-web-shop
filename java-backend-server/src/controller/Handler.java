package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Handler implements Runnable {
    private static final String GROUP_SEPARATOR = Character.toString( 29 ) ;
    private final Socket socket;
    private final Controller controller;

    private PrintWriter writer;

    public Handler(Socket socket) {
        this.socket = socket;
        this.controller = new Controller(this);
    }

    @Override
    public void run() {

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            writer = out;
            System.out.println("Client connected");


            while (true) {
                String input = in.readLine();
                System.out.println("Received: " + input);

                String[] inputArray = input.split(GROUP_SEPARATOR);
                String request = inputArray[0];
                String json = inputArray[1].replaceAll("\"", "");
                controller.handleRequest(Request.valueOf(request), json);
             }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void sendResponse(Request request, String json) {
        System.out.println("Sending response: " + request.toString() + GROUP_SEPARATOR + json);
        writer.println(request.toString() + GROUP_SEPARATOR + json + "\n");
        writer.flush();
    }






}
