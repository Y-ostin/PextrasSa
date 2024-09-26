package com.tecsup.lab08teo.version1.room

import android.content.Context
import androidx.room.Room

object CatalogoDBProvider {
    private var db: CatalogoDB? = null

    fun getDB(context: Context): CatalogoDB {
        if (db == null) {
            synchronized(CatalogoDB::class.java) {
                if (db == null) {
                    db = Room.databaseBuilder(
                        context.applicationContext,
                        CatalogoDB::class.java,
                        "db_catalogo"
                    ).fallbackToDestructiveMigration().build()
                }
            }
        }
        return db!!
    }
}