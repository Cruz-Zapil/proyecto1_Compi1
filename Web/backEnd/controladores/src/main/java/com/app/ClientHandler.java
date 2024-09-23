package com.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.app.recursos.ConexionXson;


public class ClientHandler implements Runnable {

    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());

            String mensaje = entrada.readUTF();

            String[] partes = mensaje.split("#");
            String comando = partes[0];

            /// verificar el comando o tipo de peticion que se realizo

            switch (comando) {
                case "LOGIN":
                    String peticion = partes[1];

                    try {
                        // usar el analizador Xson
                        ConexionXson conexionXson = new ConexionXson();
                        String result = conexionXson.analizarLogin(peticion);
                        String respuesta = conexionXson.mensaje;


                        if ("Usuario logeado".equals(result)) {
                            // mostrar que existe el usuario
                            salida.writeUTF("Usuario logeado"+ result);
                        } else {
                            // Si el usuario no está autenticado, redirige de nuevo al formulario con un
                            salida.writeUTF("Usuario no logeado " + result);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                        salida.writeUTF("Error de parseo: " + e.getMessage());
                    }

                    break;
                case "TRIVIAS":
                    break;

                case "SAVE_INFO":
                    break;

                default:
                    salida.writeUTF("comando no reconocido");
                    break;
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                salida.writeUTF(" hubo error en la conexion" + e.getMessage());
            } catch (IOException e1) {

                e1.printStackTrace();
            }

        }

    }

}
