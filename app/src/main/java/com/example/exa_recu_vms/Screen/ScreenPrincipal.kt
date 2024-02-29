package com.example.exa_recu_vms.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.exa_recu_vms.ViewModelAsignatura
import com.example.exa_recu_vms.data.uiStateAsignatura
import com.example.examen1viewmodel.data.Asignatura
import com.example.examen1viewmodel.data.DataSource

@Composable
fun ScreenPrincipal(
    viewModelAsignatura: ViewModelAsignatura,
    uiStateAsignatura: uiStateAsignatura,
    clickCambiarPantalla: () -> Unit,
    modifier: Modifier = Modifier,
    asignaturasList: ArrayList<Asignatura> = DataSource.asignaturas
) {

    Column {
        Text(text = "Bienvenido a la academia de Sergio/VMS",
            modifier = modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(top = 10.dp, start = 20.dp))
        Spacer(modifier = modifier.height(8.dp))
        AsignaturasScrollVertical(viewModelAsignatura, uiStateAsignatura, modifier, asignaturasList)
        Spacer(modifier = modifier.height(8.dp))
        HorasContratarEliminar(viewModelAsignatura, uiStateAsignatura, modifier)
        Spacer(modifier = modifier.height(8.dp))
        TextoGestion(viewModelAsignatura, uiStateAsignatura, modifier)
        Spacer(modifier = modifier.height(8.dp))
        Button(onClick = { clickCambiarPantalla.invoke() }, modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Text(text = "Cambiar de pantalla")
        }
    }
}

@Composable
fun TextoGestion(viewModelAsignatura: ViewModelAsignatura, uiStateAsignatura: uiStateAsignatura, modifier: Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(Color.LightGray)
        .padding(30.dp)){
        Column {

            Text(text = "Ultima acción: \n" + uiStateAsignatura.ultimaAccion,
                modifier
                    .fillMaxWidth()
                    .background(Color.Yellow))

            Text(text = "Resumen: \n" + uiStateAsignatura.resumen,
                modifier
                    .fillMaxWidth()
                    .background(Color.White))

            Text(text = "Total horas: ${uiStateAsignatura.totalHoras} \n Total precio: ${uiStateAsignatura.totalPrecio}",
                modifier
                    .fillMaxWidth()
                    .background(Color.Yellow))
        }
        }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorasContratarEliminar(viewModelAsignatura: ViewModelAsignatura, uiStateAsignatura: uiStateAsignatura, modifier: Modifier) {
    TextField(
        value = uiStateAsignatura.horasAContratarEliminar,
        onValueChange = { viewModelAsignatura.setHorasAContratarEliminar(it) } ,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        label = { Text(text = "Horas a contratar o a eliminar")}
    )
}

@Composable
fun AsignaturasScrollVertical(
    viewModelAsignatura: ViewModelAsignatura,
    uiStateAsignatura: uiStateAsignatura,
    modifier: Modifier,
    asignaturasList: ArrayList<Asignatura>
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier.height(300.dp)){
            items(asignaturasList){ asignatura -> 
                // por cada asignatura del arrayList
                Card (modifier = modifier.padding(8.dp)){
                    Text(
                        text = "Asig: ${asignatura.nombre}",
                        modifier = modifier
                            .fillMaxWidth()
                            .background(Color.Yellow)
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    )
                    Text(
                        text = "€/hora: ${asignatura.precioHora}",
                        modifier = modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    )
                    Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()){

                        Button(onClick = { viewModelAsignatura.sumarHoras(asignatura.nombre, uiStateAsignatura.horasAContratarEliminar, asignatura.precioHora) }) {
                            Text(text = "+")
                        }
                        Button(onClick = { viewModelAsignatura.restarHoras(asignatura.nombre, uiStateAsignatura.horasAContratarEliminar, asignatura.precioHora)}) {
                            Text(text = "-")
                        }

                    }
                }
        }
    }
}
