package com.example.larecette

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.larecette.data.dataclasse.Meal
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MealDetailFragment : Fragment() {

    private var isFavorite: Boolean = false

    private lateinit var mealName: TextView
    private lateinit var mealOrigin: TextView
    private lateinit var mealInstructions: TextView
    private lateinit var mealImage: ImageView
    private lateinit var favoriteButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealName = view.findViewById(R.id.mealName)
        mealOrigin = view.findViewById(R.id.mealOrigin)
        mealInstructions = view.findViewById(R.id.mealInstructions)
        mealImage = view.findViewById(R.id.mealImage)
        favoriteButton = view.findViewById(R.id.favoriteButton)

        val meal = arguments?.getParcelableCompat<Meal>("meal")

        meal?.let {
            mealName.text = it.strMeal
            mealOrigin.text = it.strArea ?: "Unknown"
            mealInstructions.text = it.strInstructions ?: "No instructions available"
            Glide.with(this)
                .load(it.strMealThumb)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(mealImage)

            favoriteButton.setOnClickListener {
                isFavorite = !isFavorite
                updateFavoriteButton()
                // Ajoutez ici le code pour gérer l'ajout ou le retrait des favoris
            }

            updateFavoriteButton()
        }
    }

    private fun updateFavoriteButton() {
        if (isFavorite) {
            favoriteButton.text = "Remove from Favorites"
        } else {
            favoriteButton.text = "Add to Favorites"
        }
    }
}

// Extension function to handle getParcelable deprecation
inline fun <reified T : Parcelable> Bundle.getParcelableCompat(key: String?): T? = when {
    android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}
