package com.example.exa_recu_vms.Screen

sealed class Screens(val ruta: String){
    object Principal: Screens("Principal")
    object Vacia: Screens("Vacia")

}
