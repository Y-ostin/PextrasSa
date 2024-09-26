package com.tecsup.lab08teo.version1.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.lab08teo.version1.room.Categorias
import com.tecsup.lab08teo.version1.viewmodel.CatalogoViewModel

@Composable
fun ContenidoCategorias (pv: PaddingValues, opcion: MutableState<String>, viewModel: CatalogoViewModel) {

    LaunchedEffect(Unit) {
        viewModel.listarCategorias()
    }

    Column (
        // modifier = androidx.compose.ui.Modifier.padding(pv),
        // verticalArrangement = Arrangement.Top
    ) {
        val categorias = viewModel.listaCategorias

        LazyColumn {
            item {
                Row (
                    modifier = Modifier
                        .fillParentMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ID",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.1f)
                    )
                    Text(
                        text = "CATEGORIA",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.5f)
                    ) //, fontWeight = FontWeight.Bold)
                    Text(
                        text = "CANT.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    ) //, fontWeight = FontWeight.Bold)
                    Text(
                        text = "Accion",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    ) //, fontWeight = FontWeight.Bold)
                }
            }

            items( categorias ) { //.listaUsuarios ) {

                Row (
                    modifier = Modifier
                        //.padding(2.dp)
                        .fillParentMaxWidth(),
                    //horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${it.idCat}", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier=Modifier.weight(0.1f))
                    Text(text = it.nombreCat, fontSize = 18.sp, modifier=Modifier.weight(0.6f)) //, fontWeight = FontWeight.Bold)
                    Text(text = it.cantCat.toString(), fontSize = 18.sp, modifier=Modifier.weight(0.1f)) //, fontWeight = FontWeight.Bold)
                    IconButton(
                        onClick = {
                            opcion.value = "Categorias.Editar"
                            viewModel.objCatActual.value = it
                        },
                        modifier=Modifier.weight(0.1f)
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(
                        onClick = {
                            opcion.value = "Categorias.Eliminar"
                            viewModel.objCatActual.value = it
                        },
                        modifier=Modifier.weight(0.1f)
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar")
                    }
                }
            }
        }
    }
}

@Composable
fun EditarCategoria(opcion: MutableState<String>, viewModel: CatalogoViewModel) {
    var id by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    LaunchedEffect(opcion.value) {
        if (opcion.value == "Categorias.Editar") {
            val objCat = viewModel.objCatActual.value
            id = objCat?.idCat.toString()
            nombre = objCat?.nombreCat ?: ""
            cantidad = objCat?.cantCat.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Spacer(Modifier.height(50.dp))
        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID (solo lectura)") },
            readOnly = true,
            singleLine = true
        )
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre: ") },
            singleLine = true
        )
        TextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad:") },
            readOnly = true,
            singleLine = true
        )
        Button(
            onClick = {
                when (opcion.value) {
                    "Categorias.Nuevo" -> {
                        val objCat = Categorias(0,nombre, 0)
                        viewModel.agregarCategoria(objCat)
                    }
                    "Categorias.Editar" -> {
                        val objCat = Categorias(id.toInt(), nombre, cantidad.toInt())
                        viewModel.actualizarCategoria(objCat)
                        Log.e("Categoria.Editar", "Nombre = ${objCat.nombreCat}")
                    }
                }

                nombre = ""
                cantidad = ""
                opcion.value = "Categorias"
            }
        ) {
            Text("Grabar", fontSize=16.sp)
        }
    }
}