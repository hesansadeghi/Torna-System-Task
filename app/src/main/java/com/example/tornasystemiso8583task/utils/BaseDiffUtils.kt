package com.example.tornasystemiso8583task.utils

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtils<T>(private val oldList:List<T>,private val newList: List<T>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] === oldList[oldItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] === oldList[oldItemPosition]
    }
}