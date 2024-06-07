package com.example.larecette.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.larecette.R
import com.example.larecette.data.dataclasse.Meal

class CategoryItemsAdapter(private val items: List<Meal>,
                           private val onMealClick: (Meal) -> Unit) :
    RecyclerView.Adapter<CategoryItemsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.strMeal

        Glide.with(holder.itemImage.context)
            .load(item.strMealThumb)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.itemImage)

        holder.itemView.setOnClickListener {
            onMealClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
    }
}
