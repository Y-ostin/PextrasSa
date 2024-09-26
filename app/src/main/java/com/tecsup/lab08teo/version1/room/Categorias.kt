package com.tecsup.lab08teo.version1.room

import androidx.compose.runtime.MutableState
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class Categorias(
    @PrimaryKey(autoGenerate = true)
    val idCat: Int = 0,
    @ColumnInfo(name = "nombre_cat")
    val nombreCat: String,
    @ColumnInfo(name = "cant_cat")
    val cantCat: Int = 0
)
