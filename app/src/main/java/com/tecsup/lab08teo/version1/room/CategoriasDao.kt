package com.tecsup.lab08teo.version1.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoriasDao {
    @Query("SELECT * FROM Categorias")
    suspend fun getCategorias(): List<Categorias>

    @Query("SELECT * FROM Categorias WHERE idCat = :idCat")
    suspend fun getCategoria(idCat: Int): Categorias

    @Insert
    suspend fun agregarCategoria(categoria: Categorias)

    @Update
    suspend fun actualizarCategoria(categoria: Categorias)

    @Delete
    suspend fun eliminarCategoria(categoria: Categorias)
}