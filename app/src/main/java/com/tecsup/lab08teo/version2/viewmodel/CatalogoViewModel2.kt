package com.tecsup.lab08teo.version2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Transaction
import com.tecsup.lab08teo.version1.room.CatalogoDB
import com.tecsup.lab08teo.version1.room.Categorias
import com.tecsup.lab08teo.version1.room.Productos
import kotlinx.coroutines.launch

class CatalogoViewModel2(private val db:CatalogoDB): ViewModel() {

    var daoCat = db.categoriasDao()
    var daoPrd = db.productosDao()

    var objCatActual = mutableStateOf<Categorias?>(null)
    var objPrdActual = mutableStateOf<Productos?>(null)

    var listaCategorias by mutableStateOf<List<Categorias>>(emptyList())
        private set

    fun listarCategorias() {
        viewModelScope.launch {
            daoCat.getCategorias().also { listaCategorias = it }
        }
    }

    fun agregarCategoria(categoria: Categorias) {
        viewModelScope.launch {
            daoCat.agregarCategoria(categoria)
        }
    }

    fun actualizarCategoria(categoria: Categorias) {
        viewModelScope.launch {
            daoCat.actualizarCategoria(categoria)
        }
    }

    fun eliminarCategoria(categoria: Categorias) {
        viewModelScope.launch {
            daoCat.eliminarCategoria(categoria)
        }
    }

    var listaProductos by mutableStateOf<List<Productos>>(emptyList())
        private set

    fun listarProductos() {
        viewModelScope.launch {
            daoPrd.getProductos().also { listaProductos = it }
        }
    }

    @Transaction
    fun agregarProducto(producto: Productos) {
        viewModelScope.launch {
            daoPrd.agregarProducto(producto)
            val idCat = producto.idCategoria
            val objCat = daoCat.getCategoria(idCat)
            val objCatMod = Categorias(objCat.idCat, objCat.nombreCat, objCat.cantCat+1)
            daoCat.actualizarCategoria(objCatMod)
        }
    }

    fun actualizarProducto(producto: Productos) {
        viewModelScope.launch {
            val prdOrg = daoPrd.getProducto(producto.idPrd)
            if (prdOrg.idCategoria != producto.idCategoria) {
                val objCatAnt = daoCat.getCategoria(prdOrg.idCategoria)
                val objCatNue = daoCat.getCategoria(producto.idCategoria)
                val objCatAntMod = Categorias(objCatAnt.idCat, objCatAnt.nombreCat, objCatAnt.cantCat-1)
                val objCatNueMod = Categorias(objCatNue.idCat, objCatNue.nombreCat, objCatNue.cantCat+1)
                daoCat.actualizarCategoria(objCatAntMod)
                daoCat.actualizarCategoria(objCatNueMod)
            }
            daoPrd.actualizarProducto(producto)
        }
    }

    @Transaction
    fun eliminarProducto(producto: Productos) {
        viewModelScope.launch {
            val objCat = daoCat.getCategoria(producto.idCategoria)
            val objCatMod = Categorias(objCat.idCat, objCat.nombreCat, objCat.cantCat-1)
            daoPrd.eliminarProducto(producto)
            daoCat.actualizarCategoria(objCatMod)
        }
    }
}