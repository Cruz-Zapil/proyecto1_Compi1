package socket

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

class SocketLoggin (private val serverIp: String, private val serverPort: Int) {

    private lateinit var socket: Socket
    private lateinit var input: DataInputStream
    private lateinit var output: DataOutputStream

    fun connect(){
        try {
            socket = Socket(serverIp, serverPort) // Conectar al servidor
            input = DataInputStream(socket.getInputStream()) // Stream de entrada
            output = DataOutputStream(socket.getOutputStream()) // Stream de salida
            println("Conexión exitosa con el servidor $serverIp:$serverPort")


        } catch (e: IOException){
            e.printStackTrace()
            println("Error al conectarse con el servidor: ${e.message}")
        }
    }

    fun enviarPeticion(peticion :String){

        try {
            output.writeUTF(peticion) // Enviar mensaje
            output.flush()
            println("Mensaje enviado: $peticion")
        } catch (e: IOException) {
            e.printStackTrace()
            println("Error al enviar mensaje: ${e.message}")
        }

    }
    fun recibirMensaje(): String? {
        return try {
            input.readUTF() // Leer respuesta del servidor
        } catch (e: IOException) {
            e.printStackTrace()
            println("Error al recibir mensaje: ${e.message}")
            null
        }
    }
    // Método para cerrar la conexión
    fun closeConnection() {
        try {
            input.close()
            output.close()
            socket.close()
            println("Conexión cerrada")
        } catch (e: IOException) {
            e.printStackTrace()
            println("Error al cerrar la conexión: ${e.message}")
        }
    }
}