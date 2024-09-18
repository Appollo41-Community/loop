package com.appollo41.loop.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Note::class
               ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun getDao(): NoteDao
    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}
