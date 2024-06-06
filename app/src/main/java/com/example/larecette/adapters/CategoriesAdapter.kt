package com.example.larecette.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.larecette.R
import com.example.larecette.data.dataclasse.Category


// Adaptateur pour afficher les catégories dans un RecyclerView
class CategoriesAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    // Crée une nouvelle vue pour un élément de la liste (ViewHolder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    // Lie les données d'une catégorie à une vue
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.strCategory

        //Glide pour charger l'image de la catégorie à partir de l'URL et l'afficher dans l'ImageView
        Glide.with(holder.categoryImage.context).load(category.strCategoryThumb).into(holder.categoryImage)
    }
    // Retourne le nombre total d'éléments dans la liste des catégories
    override fun getItemCount(): Int = categories.size

    // Classe interne représentant un élément de la liste
    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Référence au TextView pour le nom de la catégorie
        val categoryName: TextView = view.findViewById(R.id.categoryName)

        // ImageView ==> l'image de la catégorie (categoryImage)
        val categoryImage: ImageView = view.findViewById(R.id.categoryImage)
    }
}