package com.example.a512taskapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a512taskapp.domain.use_cases.SharedPref
import com.example.a512taskapp.presentation.ui.screens.HomeScreen
import com.example.a512taskapp.presentation.ui.screens.LoginScreen
import com.example.a512taskapp.presentation.ui.screens.RegisterScreen
import com.example.a512taskapp.presentation.ui.theme._512TaskAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _512TaskAppTheme {
                val navController = rememberNavController()
                val sharedPref = SharedPref(LocalContext.current)
                val isLogged = sharedPref.getIsLoggedSharedPref()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination =  if (isLogged) "home" else "login"
                    ){
                        composable(route = "login") {
                            LoginScreen(
                                innerPadding = innerPadding,
                                navController = navController,
                                sharedPref = sharedPref
                            )
                        }
                        composable(route = "register") {
                            RegisterScreen(
                                innerPadding = innerPadding,
                                navController = navController,
                                sharedPref = sharedPref
                            )
                        }
                        composable(route = "home") {
                            HomeScreen(
                                innerPadding = innerPadding,
                                navController = navController,
                                sharedPref = sharedPref
                            )
                        }
                    }
                }
            }
        }
    }
}