package com.cruzzapil.appmoviltrivia


import android.content.Intent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Trivia
import socket.SocketLoggin

/// imports de gjson


class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    /// string recibido desde el socket
    private lateinit var jsonString: String
    private var job: Job? = null
    private lateinit var socket: SocketLoggin;
    private val gson = Gson()

    /// listar los objetos de tipo trivia
    private var triviaList: List<Trivia> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Configurar la toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.navigation_view)

        // Configurar el botón del menú (hamburguesa) en la toolbar
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Mostrar saludo con el nombre del usuario obtenido del Intent
        val userName = intent.getStringExtra("username")
        val messageTextView: TextView = findViewById(R.id.messageTextView)
        messageTextView.text = "Bienvenido, $userName" // Saludo dinámico

        // Cargar el nombre de usuario en el header del menú
        val headerView = navView.getHeaderView(0)
        val userNameTextView: TextView = headerView.findViewById(R.id.userNameTextView)
        userNameTextView.text = userName // Cargar el nombre de usuario en el header

        /// llmar las trivias
        recuperarTrivias();


        // Configurar el menú de navegación
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_trivias -> {

                    val fragment = TriviasFragment.newInstance(triviaList)
                    // Cargar el fragmento de trivias
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                }

                R.id.nav_profile -> {
                    // Cargar el fragmento de perfil (debes crear el fragmento para esto)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commit()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Cargar el fragmento por defecto (Trivias en este caso)
        if (savedInstanceState == null) {
            val fragment = TriviasFragment.newInstance(triviaList)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            navView.setCheckedItem(R.id.nav_trivias)
        }

    }

    // Controlar la acción de la navegación (opcional si quieres manejar el botón "back")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun recuperarTrivias() {

        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                // Conectar al servidor de sockets
                socket = SocketLoggin("192.168.1.109", 5000)
                socket.connect()

                // realizar la peticion:
                socket.enviarPeticion("TRIVIAS")
                // Mostrar el texto en un Toast (mensaje temporal en pantalla)
                jsonString = socket.recibirMensaje().toString()
                System.out.println(" Dato recopilados " + jsonString);

                // Deserializar el JSON en una lista de objetos Trivia
                val triviaListType = object : TypeToken<List<Trivia>>() {}.type
                triviaList = gson.fromJson(jsonString, triviaListType)

                // pasamos la lista de nombres de trivias la tabla triviasFragment:

                withContext(Dispatchers.Main) {
                    // Pasar la lista de trivias al fragmento
                    val fragment = TriviasFragment.newInstance(triviaList)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                }


                socket.closeConnection()

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@HomeActivity,
                        "Error al conectar al Servidor: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


        }
    }

}





