package com.tecsup.lab08teo.version1.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Categorias::class, Productos::class],
    version = 3
)
abstract class CatalogoDB: RoomDatabase() {
    abstract fun categoriasDao(): CategoriasDao
    abstract fun productosDao(): ProductosDao
}