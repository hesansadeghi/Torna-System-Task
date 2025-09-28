package com.example.tornasystemiso8583task.repository

import com.example.tornasystemiso8583task.db.DaoISO8583
import com.example.tornasystemiso8583task.db.ISO8583Entity
import javax.inject.Inject

class Repository @Inject constructor(private val dao: DaoISO8583) {

    fun getAllIso8583() = dao.getAllISO8583()

    suspend fun insertIso8583(entity: ISO8583Entity) = dao.insertISO8583Entity(entity)

}