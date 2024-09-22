package com.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.Socket;

import com.app.gramaticas.xson.LexerXson;
import com.app.gramaticas.xson.parserXson;
import com.app.recursos.MetodoUsuario;

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

            String[] partes = mensaje.split(":");
            String comando = partes[0];

            /// verificar el comando o tipo de peticion que se realizo

            switch (comando) {
                case "LOGIN":
                    String peticion = partes[1];

                    try {
                        // usar el analizador Xson
                        Reader input = new StringReader(peticion);

                        LexerXson lexer = new LexerXson(input);
                        parserXson parser = new parserXson(lexer);
                        parser.parse();

                        // Conexión a la base de datos
                        MetodoUsuario metodoUsuario = new MetodoUsuario();
                        String resultado = metodoUsuario.loginUsuario(parser.getLoginUsuario());
                        String respuesta = metodoUsuario.getRespuesta();

                        if ("Usuario logeado".equals(resultado)) {
                            // mostrar que existe el usuario
                            salida.writeUTF("Usuario logeado"+ respuesta);
                        } else {
                            // Si el usuario no está autenticado, redirige de nuevo al formulario con un
                            salida.writeUTF("Usuario no logeado" + respuesta);
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
