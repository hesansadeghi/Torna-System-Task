package com.example.tornasystemiso8583task.utils


import android.annotation.SuppressLint
import android.util.Log
import com.imohsenb.ISO8583.builders.ISOMessageBuilder
import com.imohsenb.ISO8583.entities.ISOMessage
import com.imohsenb.ISO8583.enums.FIELDS
import com.imohsenb.ISO8583.enums.MESSAGE_FUNCTION
import com.imohsenb.ISO8583.enums.MESSAGE_ORIGIN
import com.imohsenb.ISO8583.enums.VERSION

object ISOHelper {


    @SuppressLint("DefaultLocale")
    fun buildRequest(
        stan: String,
        pan: String,
        amount: String
    ): ISOMessage {

        val packer = ISOMessageBuilder.Packer(VERSION.V1993)
            .financial()
            .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
            .processCode("920000")

        val dateTimeValue = getCurrentTimeFormatted()

        try {
            //  FIELDS enum
            packer.setField(FIELDS.F2_PAN, pan)                // PAN
            packer.setField(FIELDS.F4_AmountTransaction, amount) // Amount
            packer.setField(
                FIELDS.F7_TransmissionDataTime,
                dateTimeValue
            )       // Transmission Date & Time
            packer.setField(FIELDS.F11_STAN, stan)              // STAN

            // Currency USD
        } catch (e: Exception) {
            Log.e("ISOHelper", "Error setting field: ${e.message}")
        }

        return packer.build()
    }


}