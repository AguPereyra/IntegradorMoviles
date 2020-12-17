package com.iua.agustinpereyra.view.cattleviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.iua.agustinpereyra.databinding.CattleCardItemViewBinding
import com.iua.agustinpereyra.repository.database.entities.Cattle

class CattleCardRecyclerViewAdapter(private var cattleList: List<Cattle>,
                                    val listener: CattleCardRecyclerViewAdapter.cattleCardRecyclerViewAdapterListener) : RecyclerView.Adapter<CattleCardRecyclerViewAdapter.CattleCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CattleCardViewHolder {
        val layoutView = CattleCardItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CattleCardViewHolder(layoutView, listener)
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

    class CattleCardViewHolder(private val itemBinding: CattleCardItemViewBinding,
                               val listener: CattleCardRecyclerViewAdapter.cattleCardRecyclerViewAdapterListener) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnLongClickListener, View.OnClickListener{

        init {
            itemBinding.root.setOnLongClickListener(this)
            itemBinding.root.setOnClickListener(this)
        }

        override fun onLongClick(view: View?): Boolean {
            val cardView = view as MaterialCardView
            cardView.isChecked = !cardView.isChecked
            listener.onLongClickAction(itemBinding.cattleCaravan.text.toString())
            return true
        }

        override fun onClick(view: View?) {
            listener.onClickAction(itemBinding.cattleCaravan.text.toString())
        }

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

    /**
     * Interface that is used by the recyclerViewAdapter to set
     * the actions to apply on click and long click of items
     */
    interface cattleCardRecyclerViewAdapterListener {
        /**
         * onClickAction implements what should be done when a click over an item is called.
         * It passes the caravan of the clicked bovine through parameters
         */
        fun onClickAction(caravan: String)
        /**
         * onLongClickAction must implement what should happen when an item in
         * the recycler view receives a long click. In this case, the setup of
         * the action mode and its corresponding actionModeCallback.
         * Parameters:
         *      caravan: String. The caravan of the selected cattle.
         */
        fun onLongClickAction(caravan: String)
    }
}