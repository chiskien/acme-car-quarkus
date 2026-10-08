package com.cremedelacreme;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SynchronousEcho {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(3000));
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(clientHandler(socket)).start();
        }
    }

    private static Runnable clientHandler(Socket socket) {
        return () -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line = "";
                while (!"/quit".equals(line)) {
                    line = reader.readLine();
                    System.out.println("-" + line);
                    printWriter.write(line + "\n");
                    printWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
