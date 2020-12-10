package com.iua.agustinpereyra.view.bovine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iua.agustinpereyra.databinding.BovineDataCardItemViewBinding
import com.iua.agustinpereyra.model.BovineDataField

class BovineDataRecyclerViewAdapter(private val bovineData: List<BovineDataField>) : RecyclerView.Adapter<BovineDataRecyclerViewAdapter.BovineDataCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BovineDataCardViewHolder {
        val layoutView = BovineDataCardItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BovineDataCardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: BovineDataCardViewHolder, position: Int) {
        if (position < bovineData.size) {
            val bovineData = bovineData[position]
            holder.bind(bovineData)
        }
    }

    override fun getItemCount(): Int {
        return bovineData.size
    }

    class BovineDataCardViewHolder(private val itemBinding: BovineDataCardItemViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(bovineData: BovineDataField) {
            itemBinding.bovineDataTitle.text = bovineData.title
            itemBinding.bovineDataField1.text = bovineData.field1
            itemBinding.bovineDataField2.text = bovineData.field2
        }
    }

}