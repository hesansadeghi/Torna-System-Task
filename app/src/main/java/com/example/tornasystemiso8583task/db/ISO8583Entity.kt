package com.example.tornasystemiso8583task.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tornasystemiso8583task.utils.Constant

@Entity(tableName = Constant.DB_TABLE_NAME)
data class ISO8583Entity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var message: String = "",
    var f2Pan: String = "",
    var f4AmountTransaction: String = "",
    var f7TransmissionDataTime: String = "",
    var f11Stan: String = "",
    var mti: String = "",
    var bitmap: String = ""
)
