package com.iua.agustinpereyra.view.cattleviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.databinding.CattleCardItemViewBinding
import com.iua.agustinpereyra.repository.database.entities.Cattle

class CattleCardRecyclerViewAdapter(private var cattleList: List<Cattle>) : RecyclerView.Adapter<CattleCardRecyclerViewAdapter.CattleCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CattleCardViewHolder {
        val layoutView = CattleCardItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CattleCardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: CattleCardViewHolder, position: Int) {
        if (position < cattleList.size) {
            val bovine = cattleList[position]
            holder.bind(bovine)
        }
    }

    override fun getItemCount(): Int {
        return cattleList.size
    }

    fun setCattle(newList: List<Cattle>) {
        cattleList = newList
        notifyDataSetChanged()
    }

    class CattleCardViewHolder(private val itemBinding: CattleCardItemViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(bovine: Cattle) {
            itemBinding.cattleCaravan.text = bovine.caravan
            itemBinding.cattleWeight.text = bovine.weight.toString() + " Kg"
            itemBinding.cattleSex.text = bovine.getSexAsString()
            // Load image
            Glide
                .with(this.itemView.context)
                .load(bovine.imgUrl)
                .into(itemBinding.cattleCardImage)
        }
    }
}