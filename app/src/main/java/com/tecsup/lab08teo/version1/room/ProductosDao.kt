package com.tecsup.lab08teo.version1.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductosDao {
    @Query("SELECT * FROM productos")
    suspend fun getProductos(): List<Productos>

    @Query("SELECT * FROM productos WHERE idPrd = :idPrd")
    suspend fun getProducto(idPrd: Int): Productos

    @Insert
    suspend fun agregarProducto(producto: Productos)

    @Update
    suspend fun actualizarProducto(producto: Productos)

    @Delete
    suspend fun eliminarProducto(producto: Productos)
}