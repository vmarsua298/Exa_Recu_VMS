package com.example.exa_recu_vms

import androidx.lifecycle.ViewModel
import com.example.exa_recu_vms.data.AsignaturaAgregadas
import com.example.exa_recu_vms.data.uiStateAsignatura
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.Exception

class ViewModelAsignatura: ViewModel() {

    private val _uiState = MutableStateFlow(uiStateAsignatura())
    val uiState: StateFlow<uiStateAsignatura> = _uiState.asStateFlow()
    private var _asignaturasContratadas = _uiState.value.listaAsignaturasCreadas

    private fun tratarPrecio(): Int {
        var precioTotal: Int = 0
        _asignaturasContratadas.forEach {  asignatura ->
            precioTotal += (asignatura.precioAsignatura*asignatura.horas)
        }
        return precioTotal
    }


    private fun tratarHoras(): Int {
        var horasTotales: Int = 0
        _asignaturasContratadas.forEach {  asignatura ->
            horasTotales += asignatura.horas
        }
        return horasTotales
    }

    private fun tratarResumen(): String {
        var resultado: String = ""

        _asignaturasContratadas.forEach {  asignatura ->
            resultado += "Asig: ${asignatura.nombre} precio/hora ${asignatura.precioAsignatura} total horas: ${asignatura.horas}\n"
        }

        if(resultado != ""){
            return resultado
        } else {
            return "No hay  nada que mostrar (defecto)"
        }
    }

    fun setHorasAContratarEliminar(horasAContratarEliminar: String) {
        _uiState.update { currentState ->
            currentState.copy( horasAContratarEliminar = horasAContratarEliminar)
        }
    }

    fun restarHoras(
        nombreAsignatura: String,
        horasAContratarEliminar: String,
        precioAsignatura: Int
    ) {
        try{
            var horasNumerica: Int = horasAContratarEliminar.toInt() // comprobamos que la resta de horas que enviamos es numerica

            if (horasNumerica>0){ // no se sacar la excepcion de >0 asi que actualizo la ultima acción y no hace ningun otro proceso más

                val pos: Int = _asignaturasContratadas.indexOfFirst { asignatura -> asignatura.nombre==nombreAsignatura } // observamos la posicion de la asignatura

                // entra en el if si detecta la asignatura sino en el else
                if( pos == -1 ){
                    _uiState.update { currentState ->
                        currentState.copy(
                            ultimaAccion = "No hay horas asignadas a esa tarea",
                        )
                    }
                } else {

                    val asignaturaTomada: AsignaturaAgregadas = AsignaturaAgregadas(nombreAsignatura, horasNumerica, precioAsignatura)

                    _asignaturasContratadas[pos].horas -= horasNumerica // resto las horas

                    if (_asignaturasContratadas[pos].horas<=0){
                        _asignaturasContratadas.removeAt(pos) // elimina la posicion si no tiene horas o tiene horas negativas
                    }

                    _uiState.update { currentState ->
                        currentState.copy(
                            horasAContratarEliminar = horasAContratarEliminar,
                            listaAsignaturasCreadas = _asignaturasContratadas,
                            ultimaAccion = "Se han restado $horasNumerica horas de ${asignaturaTomada.nombre} a ${asignaturaTomada.precioAsignatura}€",
                            resumen = tratarResumen(),
                            totalHoras = tratarHoras(),
                            totalPrecio = tratarPrecio()
                        )
                    }

                }

            } else {
                _uiState.update { currentState ->
                    currentState.copy( ultimaAccion = "Debe de ser mayor a 0")
                }
            }

        }catch (e: Exception){
            // salta la excepcion
            _uiState.update { currentState ->
                currentState.copy( ultimaAccion = "Debe de ser numerica")
            }
        }
    }

    fun sumarHoras(nombreAsignatura: String, horasAContratarEliminar: String, precioAsignatura: Int) {

        try{
            val horasNumerica: Int = horasAContratarEliminar.toInt() // comprobamos que la suma de horas que enviamos es numerica

            if (horasNumerica>0) { // no se sacar la excepcion de >0 asi que actualizo la ultima acción y no hace ningun otro proceso más
                val nuevaAsignatura: AsignaturaAgregadas =
                    AsignaturaAgregadas(nombreAsignatura, horasNumerica, precioAsignatura)

                val pos: Int =
                    _asignaturasContratadas.indexOfFirst { asignatura -> asignatura.nombre == nombreAsignatura } // observamos la posicion de la asignatura

                // entra en el if si esta vacio sino en el else
                if (pos == -1) {
                    _asignaturasContratadas.add(nuevaAsignatura) // añado una nueva asignatura

                } else {
                    _asignaturasContratadas[pos].horas += horasNumerica // sumo las horas
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        horasAContratarEliminar = horasAContratarEliminar,
                        listaAsignaturasCreadas = _asignaturasContratadas,
                        ultimaAccion = "Se han añadido $horasNumerica horas de ${nuevaAsignatura.nombre} a ${nuevaAsignatura.precioAsignatura}€",
                        resumen = tratarResumen(),
                        totalHoras = tratarHoras(),
                        totalPrecio = tratarPrecio()
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy( ultimaAccion = "Debe de ser mayor a 0")
                }
            }
        }catch (e: Exception){
            // salta la excepcion
            _uiState.update { currentState ->
                currentState.copy( ultimaAccion = "Debe de ser numerica")
            }
        }
    }
}