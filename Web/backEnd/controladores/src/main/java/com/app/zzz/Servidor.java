package com.app.zzz;

import java.io.*;
import java.net.*;

public class Servidor {
    static int PUERTO = 5000;
    ServerSocket servidor;
    Socket socket;
    DataOutputStream salida;
    DataInputStream entrada;

    public void iniciarServidor() {
        try {
            // Iniciar el servidor
            servidor = new ServerSocket(PUERTO);
            System.out.println("Esperando conexión...");
            
            // Esperar a que el cliente se conecte
            socket = servidor.accept();  
            System.out.println("Cliente conectado desde: " + socket.getInetAddress());

            // Inicializar los flujos de entrada y salida
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());

            // Recibir mensaje del cliente
            String mensajeRecibido = entrada.readUTF();
            System.out.println("Mensaje recibido del cliente: " + mensajeRecibido);

            // Enviar confirmación al cliente
            String mensajeConfirmacion = "true";
            salida.writeUTF(mensajeConfirmacion);

            // Cerrar recursos
            entrada.close();
            salida.close();
            socket.close();
            servidor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciarServidor();
    }
}
