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

class MealsAdapter(
    private val meals: List<Meal>,
    private val onMealClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_meal_detail, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.mealName.text = meal.strMeal

        Glide.with(holder.mealImage.context)
            .load(meal.strMealThumb)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.mealImage)

        holder.itemView.setOnClickListener {
            onMealClick(meal)
        }
    }

    override fun getItemCount(): Int = meals.size

    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mealName: TextView = view.findViewById(R.id.mealName)
        val mealImage: ImageView = view.findViewById(R.id.mealImage)
    }
}
