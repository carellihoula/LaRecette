package com.example.larecette

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Trouver le BottomNavigationView par son ID défini dans le fichier XML de mise en page
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        // Trouver le NavController associé au NavHostFragment
        val navController = Navigation.findNavController(this,R.id.frag_host)
        // Configurer le BottomNavigationView avec le NavController
        // Cela lie le NavController au BottomNavigationView,
        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}