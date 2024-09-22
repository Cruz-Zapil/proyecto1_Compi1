package com.cruzzapil.appmoviltrivia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val messageTextView: TextView = findViewById(R.id.messageTextView)
        val message = intent.getStringExtra("MESSAGE") // Recibir el mensaje
        messageTextView.text = message // Mostrar el mensaje en un TextView


    }


}
