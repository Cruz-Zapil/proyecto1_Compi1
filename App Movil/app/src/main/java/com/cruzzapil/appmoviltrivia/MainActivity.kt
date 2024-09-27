package com.cruzzapil.appmoviltrivia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import socket.SocketLoggin

class MainActivity : AppCompatActivity() {

    // Obtener las referencias de los elementos de la vista
    private lateinit var buttonEnviar: Button   // Referencia al botón
    private lateinit var editText: EditText // Referencia al EditText
    private lateinit var socket: SocketLoggin ;
    private lateinit var mensaje :String
    // job
    private var job: Job?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Asignar las referencias de las vistas después de setContentView
        editText = findViewById(R.id.editText)
        buttonEnviar = findViewById(R.id.buttonEnviar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        buttonEnviar.setOnClickListener {
            val textoIngresado = editText.text.toString()
            // mostramos el texot en un toast
            realizarPeticion(textoIngresado)
        }
    }

    fun realizarPeticion(peticion: String){
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                // Conectar al servidor de sockets
                socket = SocketLoggin("192.168.1.109",5000)
                socket.connect()

                // realizar la peticion:
                socket.enviarPeticion("LOGIN#$peticion")
                // Mostrar el texto en un Toast (mensaje temporal en pantalla)
                 mensaje = socket.recibirMensaje().toString()
                 System.out.println(" dato recopilados "+ mensaje );
                val partes = mensaje.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                // Mostrar el mensaje en la UI
                withContext(Dispatchers.Main) {

                    if (partes[0] == "Usuario logeado") { // Cambia esto por tu condición
                        val ventana = Intent(this@MainActivity, HomeActivity::class.java)
                        ventana.putExtra("username", partes[1]) // Mensaje para pasar a la nueva actividad
                        startActivity(ventana)
                    } else {
                        Toast.makeText(this@MainActivity, "Respuesta del servidor: $partes[1]", Toast.LENGTH_LONG).show()
                    }
                }
                socket.closeConnection()
            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error al conectar al Servidor: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}