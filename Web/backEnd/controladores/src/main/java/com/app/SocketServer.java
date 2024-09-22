package com.app;


import java.net.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SocketServer implements ServletContextListener {

    private ServerSocket serverSocket;
    private Thread serverThread;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // cuando el servlet se inicializa, tambien se inicializa el hilo del servidor
       
        serverThread = new Thread(() -> {
            
            try {
                serverSocket = new ServerSocket(5000);

                while (true) {

                    Socket socket = serverSocket.accept();
                    System.err.println("Nueva conexión entrante: " + socket.getInetAddress());

                    new Thread(new ClientHandler(socket)).start();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // cuando el servlet se destruye, tambien se cierra el hilo del servidor
        try {

            if (serverSocket != null && !serverSocket.isClosed()){

                serverSocket.close();
                serverThread.interrupt();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
    
}
