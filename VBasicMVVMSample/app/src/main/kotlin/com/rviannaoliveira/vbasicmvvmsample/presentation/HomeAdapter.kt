package com.rviannaoliveira.vbasicmvvmsample.presentation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rviannaoliveira.core.load
import com.rviannaoliveira.repository.marvel.presentation.model.MarvelCharacter
import com.rviannaoliveira.vbasicmvvmsample.R
import kotlinx.android.synthetic.main.item_row_layout.view.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ItemsViewHolder>(){
    private val items = mutableListOf<MarvelCharacter>()

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = items[position]
        holder.description.text = item.name
        holder.image.load(item.image!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ItemsViewHolder(view)
    }

    fun loadMarvelCharacters(items : List<MarvelCharacter>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =  items.size

    inner class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.imageItem
        val description: TextView = itemView.descriptionItem
    }
}
