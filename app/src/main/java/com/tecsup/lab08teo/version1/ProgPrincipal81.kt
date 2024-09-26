package com.tecsup.lab08teo.version1

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.lab08teo.version1.room.CatalogoDBProvider
import com.tecsup.lab08teo.version1.view.ContenidoCategorias
import com.tecsup.lab08teo.version1.view.ContenidoProductos
import com.tecsup.lab08teo.version1.view.EditarCategoria
import com.tecsup.lab08teo.version1.view.EditarProducto
import com.tecsup.lab08teo.version1.viewmodel.CatalogoViewModel

@Composable
fun ProgPrincipal81() {
    val opcion = remember { mutableStateOf("Inicio") }
    val context = LocalContext.current
    val db = CatalogoDBProvider.getDB(context)
    val viewModel = CatalogoViewModel(db)

    Scaffold (
        modifier = Modifier.padding(top=40.dp),
        topBar = { BarraSuperior(opcion) },
        bottomBar = { BarraNavegacion(opcion) },
        floatingActionButton = { BotonFAB(opcion, viewModel) },
        content = { paddingValues -> Contenido(paddingValues, opcion, viewModel) }
    )
}

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(opcion: MutableState<String>) {
    TopAppBar(
        title = {
            Text(
                text = "Simple Catalogo\n${opcion.value}",
                fontSize = 30.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2D557F),
            titleContentColor = Color.White
        )
    )
}

@Composable
fun Contenido(paddingValues: PaddingValues, opcion: MutableState<String>, viewModel: CatalogoViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (opcion.value) {
            "Inicio" -> Inicio()
            "Categorias" -> ContenidoCategorias(paddingValues, opcion, viewModel)
            "Categorias.Nuevo" -> EditarCategoria(opcion, viewModel)
            "Categorias.Editar" -> EditarCategoria(opcion, viewModel)
            "Categorias.Eliminar" -> {
                viewModel.eliminarCategoria(viewModel.objCatActual.value!!)
                opcion.value = "Categorias"
            }
            "Productos" -> ContenidoProductos(paddingValues, opcion, viewModel)
            "Productos.Nuevo" -> EditarProducto(opcion, viewModel)
            "Productos.Editar" -> EditarProducto(opcion, viewModel)
            "Productos.Eliminar" -> {viewModel.eliminarProducto(viewModel.objPrdActual.value!!)
                opcion.value = "Productos"
            }
        }
    }
}

@Composable
fun Inicio() {
    Text("Inicio")
}

@Composable
fun Productos() {
    Text("Productos")
}

@Composable
fun BarraNavegacion(opcion: MutableState<String>) {
    NavigationBar(
        containerColor = Color.LightGray
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = opcion.value == "Inicio",
            onClick = { opcion.value = "Inicio" }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Favorite, contentDescription = "Favoritos") },
            label = { Text("Categorias") },
            selected = opcion.value == "Categorias",
            onClick = { opcion.value = "Categorias" }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Notifications, contentDescription = "Notificaciones") },
            label = { Text("Productos") },
            selected = opcion.value == "Productos",
            onClick = { opcion.value = "Productos" }
        )
    }
}

@Composable
fun BotonFAB(opcion: MutableState<String>, viewModel: CatalogoViewModel) {
    if (opcion.value == "Categorias" || opcion.value == "Productos") {
        FloatingActionButton(
            containerColor = Color.Magenta,
            contentColor = Color.White,
            onClick = {
                opcion.value += ".Nuevo"
            }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add"
            )
        }
    }
}
