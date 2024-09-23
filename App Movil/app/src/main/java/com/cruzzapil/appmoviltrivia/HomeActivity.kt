package com.cruzzapil.appmoviltrivia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.navigation_view)

        // Carga del nombre de usuario en el header del menú
        val headerView = navView.getHeaderView(0)
        val userNameTextView: TextView = headerView.findViewById(R.id.userNameTextView)
        userNameTextView.text = "Juan Pérez" // Aquí puedes cargar el nombre del usuario dinámicamente

// Configurar el menú de navegación
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_trivias -> {
                    // Cargar el fragmento de trivias
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, TriviasFragment())
                        .commit()
                }
                R.id.nav_profile -> {
                    // Cargar el fragmento de perfil (puedes crear otro fragmento para esto)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commit()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Cargar el fragmento por defecto (puede ser trivias o cualquier otro)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TriviasFragment())
                .commit()
            navView.setCheckedItem(R.id.nav_trivias)
        }
    }
}