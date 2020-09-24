package com.iua.agustinpereyra.controller.cattleviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.model.Cattle

class CattleCardRecyclerViewAdapter(private val cattleList: List<Cattle>) : RecyclerView.Adapter<CattleCardRecyclerViewAdapter.CattleCardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CattleCardViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.cattle_card_holder, parent, false)
        return CattleCardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: CattleCardViewHolder, position: Int) {
        if (position < cattleList.size) {
            val bovine = cattleList[position]
            holder.caravan.text = bovine.caravan
            holder.weight.text = bovine.weight.toString() + " Kg"
            holder.image.setImageResource(bovine.imageId)
        }
    }

    override fun getItemCount(): Int {
        return cattleList.size
    }

    class CattleCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val caravan = itemView.findViewById<TextView>(R.id.cattle_caravan)
        val weight = itemView.findViewById<TextView>(R.id.cattle_weight)
        val image = itemView.findViewById<ImageView>(R.id.cattle_card_image)
    }
}