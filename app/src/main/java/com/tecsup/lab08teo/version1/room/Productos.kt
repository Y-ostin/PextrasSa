package com.tecsup.lab08teo.version1.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "productos",
    foreignKeys = [ForeignKey(
        entity = Categorias::class,
        parentColumns = ["idCat"],
        childColumns = ["id_categoria"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Productos(
    @PrimaryKey(autoGenerate = true)
    val idPrd: Int = 0,
    @ColumnInfo(name = "nombre_prd")
    val nombrePrd: String,
    @ColumnInfo(name = "precio_prd")
    val precio: Double,
    @ColumnInfo(name = "id_categoria")
    val idCategoria: Int
)
