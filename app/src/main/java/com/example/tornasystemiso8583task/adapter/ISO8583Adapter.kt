package com.example.tornasystemiso8583task.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.tornasystemiso8583task.databinding.Ios8583MessageItemBinding
import com.example.tornasystemiso8583task.db.ISO8583Entity
import com.example.tornasystemiso8583task.utils.BaseDiffUtils
import javax.inject.Inject

class PopularAdapter @Inject constructor() : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    lateinit var binding: Ios8583MessageItemBinding

    private var list = emptyList<ISO8583Entity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding =
            Ios8583MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(newData: List<ISO8583Entity>) {

        val adapterDiffUtils = BaseDiffUtils(list, newData)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        list = newData
        diffUtils.dispatchUpdatesTo(this)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(entity: ISO8583Entity) {

            binding.apply {

                tvFullMessageValue.text = entity.message
                tvMtiValue.text = entity.mti
                tvBitmapValue.text = entity.bitmap
                tvStanValue.text = entity.f11Stan
                tvPanValue.text = entity.f2Pan
                tvDataTimeValue.text = entity.f7TransmissionDataTime
                tvAmountTransactionValue.text = entity.f4AmountTransaction

                cardMain.setOnClickListener {

                    onItemClickListener?.let {

                        it(entity.message)
                    }
                }

            }
        }
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnClickListener(listener: (String) -> Unit) {

        onItemClickListener = listener
    }


}