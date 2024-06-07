package com.example.larecette

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.larecette.data.dataclasse.MealDetail
import com.example.larecette.data.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealDetailFragment : Fragment() {

    private lateinit var mealImage: ImageView
    private lateinit var mealName: TextView
    private lateinit var mealOrigin: TextView
    private lateinit var mealInstructions: TextView
    private lateinit var mealIngredients: TextView
    private lateinit var mealVideo: Button
    private var mealYoutubeUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealImage = view.findViewById(R.id.mealImage)
        mealName = view.findViewById(R.id.mealName)
        mealOrigin = view.findViewById(R.id.mealOrigin)
        mealInstructions = view.findViewById(R.id.mealInstructions)
        mealIngredients = view.findViewById(R.id.mealIngredients)
        mealVideo = view.findViewById(R.id.mealVideo)

        val mealId = arguments?.getString("mealId")

        mealId?.let {
            fetchMealDetails(it)
        }

        mealVideo.setOnClickListener {
            mealYoutubeUrl?.let { url ->
                val videoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(videoIntent)
            }
        }
    }

    private fun fetchMealDetails(mealId: String) {
        val apiService = ApiService.create()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getMealDetails(mealId)
                val meal = response.meals.firstOrNull()

                meal?.let {
                    CoroutineScope(Dispatchers.Main).launch {
                        mealName.text = it.strMeal
                        mealOrigin.text = "Origin: ${it.strArea}"
                        mealInstructions.text = it.strInstructions
                        mealIngredients.text = formatIngredients(it)
                        Glide.with(this@MealDetailFragment)
                            .load(it.strMealThumb)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(mealImage)

                        mealYoutubeUrl = it.strYoutube
                        mealVideo.isEnabled = !it.strYoutube.isNullOrEmpty()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun formatIngredients(meal: MealDetail): String {
        val ingredients = StringBuilder()
        for (i in 1..20) {
            val ingredientField = meal::class.java.getDeclaredField("strIngredient$i")
            val measureField = meal::class.java.getDeclaredField("strMeasure$i")
            ingredientField.isAccessible = true
            measureField.isAccessible = true
            val ingredient = ingredientField.get(meal) as? String
            val measure = measureField.get(meal) as? String
            if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                ingredients.append("$ingredient - $measure\n")
            }
        }
        return ingredients.toString()
    }
}
