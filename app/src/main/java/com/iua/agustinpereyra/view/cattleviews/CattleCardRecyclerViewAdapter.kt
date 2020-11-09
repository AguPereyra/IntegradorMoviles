package com.iua.agustinpereyra.view.cattleviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.repository.database.entities.Cattle

class CattleCardRecyclerViewAdapter(private var cattleList: List<Cattle>) : RecyclerView.Adapter<CattleCardRecyclerViewAdapter.CattleCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CattleCardViewHolder {
        val layoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cattle_card_item_view, parent, false)
        return CattleCardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: CattleCardViewHolder, position: Int) {
        if (position < cattleList.size) {
            val bovine = cattleList[position]
            holder.caravan.text = bovine.caravan
            holder.weight.text = bovine.weight.toString() + " Kg"
        }
    }

    override fun getItemCount(): Int {
        return cattleList.size
    }

    fun setCattle(newList: List<Cattle>) {
        cattleList = newList
        notifyDataSetChanged()
    }

    class CattleCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val caravan = itemView.findViewById<TextView>(R.id.cattle_caravan)
        val weight = itemView.findViewById<TextView>(R.id.cattle_weight)
        val image = itemView.findViewById<ImageView>(R.id.cattle_card_image)
    }
}