package com.example.tornasystemiso8583task.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun View.hideKeyBoard() {

    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun getCurrentTimeFormatted(): String {

    val calendar = Calendar.getInstance()

    val formatter = SimpleDateFormat("MMddHHmmss", Locale.getDefault())
    val formattedTime = formatter.format(calendar.time)

    return formattedTime
}


fun RecyclerView.setInit(
    customAdapter: RecyclerView.Adapter<*>,
    linearLayoutManager: LinearLayoutManager
) {

    this.apply {

        layoutManager = linearLayoutManager
        setHasFixedSize(true)
        adapter = customAdapter
    }
}


fun generateStan(size: Int): String {

    return String.format(Locale.getDefault(), "%06d", size + 1)
}

fun generateAmount(amount: Long): String {

    return String.format(Locale.getDefault(),"%012d", amount)
}


fun Context.copyToClipBoard(text: String){

    val clipboard: ClipboardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
}

