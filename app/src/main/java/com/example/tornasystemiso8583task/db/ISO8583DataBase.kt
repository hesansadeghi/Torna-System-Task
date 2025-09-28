package com.example.tornasystemiso8583task.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ISO8583Entity::class],
    version = 1,
    exportSchema = false
)
abstract class ISO8583DataBase : RoomDatabase() {

    abstract fun dao(): DaoISO8583
}