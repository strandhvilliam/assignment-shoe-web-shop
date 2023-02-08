package controller;

import java.net.ServerSocket;

public class ServerListener {

    public ServerListener() {

        try (ServerSocket ss = new ServerSocket(9000)) {
            while (true) {
                new Thread(new Handler(
                        ss.accept()))
                        .start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new ServerListener();
    }
}
