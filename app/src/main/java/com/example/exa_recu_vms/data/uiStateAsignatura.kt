package com.example.exa_recu_vms.data

import com.example.examen1viewmodel.data.Asignatura
import kotlinx.coroutines.flow.MutableStateFlow

data class uiStateAsignatura(
    val resumen: String = "No hay nada que mostrar (defecto)",
    val ultimaAccion: String = "No has hecho ninguna accion",
    val horasAContratarEliminar: String = "",
    val totalHoras: Int = 0,
    val totalPrecio: Int = 0,
    val listaAsignaturasCreadas: ArrayList<AsignaturaAgregadas> = ArrayList() // una lista que almacena el resumen que es tipo AsignaturaAgregadas
)

