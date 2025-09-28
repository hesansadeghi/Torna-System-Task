package com.example.tornasystemiso8583task.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tornasystemiso8583task.utils.Constant.DB_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoISO8583 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertISO8583Entity(entity: ISO8583Entity)


    @Query("SELECT * FROM $DB_TABLE_NAME")
    fun getAllISO8583(): Flow<List<ISO8583Entity>>


}