package com.tecsup.lab08teo.version1.view

import android.util.Log
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.tecsup.lab08teo.version1.room.Productos
import com.tecsup.lab08teo.version1.viewmodel.CatalogoViewModel

@Composable
fun ContenidoProductos (pv: PaddingValues, opcion: MutableState<String>, viewModel: CatalogoViewModel) {

    LaunchedEffect(Unit) {
        viewModel.listarProductos()
    }

    Column (
        // modifier = androidx.compose.ui.Modifier.padding(pv),
        // verticalArrangement = Arrangement.Top
    ) {
        val productos = viewModel.listaProductos

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
                        text = "PRODUCTO",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.45f)
                    ) //, fontWeight = FontWeight.Bold)
                    Text(
                        text = "PRECIO",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.25f)
                    ) //, fontWeight = FontWeight.Bold)
                    Text(
                        text = "Accion",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.2f)
                    ) //, fontWeight = FontWeight.Bold)
                }
            }

            items( productos ) { //.listaUsuarios ) {

                Row (
                    modifier = Modifier
                        //.padding(2.dp)
                        .fillParentMaxWidth(),
                    //horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${it.idPrd}", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier= Modifier.weight(0.1f))
                    Text(text = it.nombrePrd, fontSize = 18.sp, modifier= Modifier.weight(0.45f)) //, fontWeight = FontWeight.Bold)
                    Text(text = it.precio.toString(), fontSize = 18.sp, modifier= Modifier.weight(0.25f)) //, fontWeight = FontWeight.Bold)
                    IconButton(
                        onClick = {
                            opcion.value = "Productos.Editar"
                            viewModel.objPrdActual.value = it
                        },
                        modifier= Modifier.weight(0.1f)
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(
                        onClick = {
                            opcion.value = "Productos.Eliminar"
                            viewModel.objPrdActual.value = it
                        },
                        modifier= Modifier.weight(0.1f)
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar")
                    }
                }
            }
        }
    }
}

@Composable
fun EditarProducto(opcion: MutableState<String>, viewModel: CatalogoViewModel) {
    var id by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var selectedCategoria by remember { mutableStateOf<Categorias?>(null) }
    var expanded by remember { mutableStateOf(false) } // Controla la expansión del DropdownMenu

    // Obtener las categorías desde el ViewModel
    LaunchedEffect(Unit) {
        viewModel.listarCategorias() // Cargar las categorías
    }

    // Cargar los datos del producto a editar
    LaunchedEffect(opcion.value) {
        if (opcion.value == "Productos.Editar") {
            val objPrd = viewModel.objPrdActual.value
            id = objPrd?.idPrd.toString()
            nombre = objPrd?.nombrePrd ?: ""
            precio = objPrd?.precio.toString()
            selectedCategoria = viewModel.listaCategorias.find { it.idCat == objPrd?.idCategoria }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(50.dp))

        // Campo para ID (solo lectura)
        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID (solo lectura)") },
            readOnly = true,
            singleLine = true
        )

        // Campo para nombre
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre:") },
            singleLine = true
        )

        // Campo para precio
        TextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio:") },
            singleLine = true
        )

        // Texto que muestra la categoría seleccionada
        Text("Categoría: ${selectedCategoria?.nombreCat ?: "Seleccionar"}")

        // Botón para abrir el DropdownMenu
        Button(onClick = { expanded = true }) {
            Text(text = "Seleccionar Categoría")
        }

        // Dropdown menu para mostrar las categorías
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // Iterar sobre la lista de categorías obtenidas desde el ViewModel
            viewModel.listaCategorias.forEach { categoria ->
                DropdownMenuItem(
                    text = {
                        Text(text = categoria.nombreCat) // Mostrar el nombre de la categoría
                    },
                    onClick = {
                        selectedCategoria = categoria // Actualizar la categoría seleccionada
                        expanded = false // Cerrar el menú
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para grabar el producto
        Button(
            onClick = {
                when (opcion.value) {
                    "Productos.Nuevo" -> {
                        val objPrd = Productos(0, nombre, precio.toDouble(), selectedCategoria?.idCat ?: 0)
                        viewModel.agregarProducto(objPrd)
                    }
                    "Productos.Editar" -> {
                        val objPrd = Productos(id.toInt(), nombre, precio.toDouble(), selectedCategoria?.idCat ?: 0)
                        viewModel.actualizarProducto(objPrd)
                    }
                }

                // Limpiar los campos
                nombre = ""
                precio = ""
                selectedCategoria = null
                opcion.value = "Productos"
            }
        ) {
            Text("Grabar", fontSize = 16.sp)
        }
    }
}
