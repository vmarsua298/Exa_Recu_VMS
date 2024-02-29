package com.example.exa_recu_vms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exa_recu_vms.Screen.ScreenPrincipal
import com.example.exa_recu_vms.Screen.ScreenVacia
import com.example.exa_recu_vms.Screen.Screens
import com.example.exa_recu_vms.data.uiStateAsignatura
import com.example.exa_recu_vms.ui.theme.Exa_Recu_VMSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exa_Recu_VMSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    fun AppNavigation(navController: NavHostController = rememberNavController()) {
            val viewModelAsignatura:ViewModelAsignatura = viewModel()
        val uiStateAsignatura by viewModelAsignatura.uiState.collectAsState()

        NavHost(navController = navController, startDestination = Screens.Principal.ruta){
            composable( route = Screens.Principal.ruta){
                ScreenPrincipal( viewModelAsignatura = viewModelAsignatura, uiStateAsignatura = uiStateAsignatura, clickCambiarPantalla = {navController.navigate(Screens.Vacia.ruta)})
            }
            composable( route = Screens.Vacia.ruta){
                ScreenVacia( )
            }
        }
    }
}

