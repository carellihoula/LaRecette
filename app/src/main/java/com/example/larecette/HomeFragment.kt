package com.example.larecette

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.larecette.adapters.CategoriesAdapter
import com.example.larecette.adapters.PopularMealsAdapter
import com.example.larecette.data.dataclasse.Category
import com.example.larecette.data.dataclasse.Meal
import com.example.larecette.data.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var apiService: ApiService
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var popularMealsAdapter: PopularMealsAdapter
    private lateinit var rvCategories: RecyclerView
    private lateinit var rvPopularMeals: RecyclerView
    private lateinit var cardRandomMeal: CardView
    private lateinit var mealImage: ImageView
    private lateinit var mealName: TextView
    private lateinit var btnRandomMeal: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rvCategories = view.findViewById(R.id.rv_categories)
        rvPopularMeals = view.findViewById(R.id.rv_popular_meals)
        cardRandomMeal = view.findViewById(R.id.card_random_meal)
        mealImage = view.findViewById(R.id.mealImage)
        mealName = view.findViewById(R.id.mealName)
        btnRandomMeal = view.findViewById(R.id.btn_random_meal)

        apiService = ApiService.create()

        fetchCategories()
        fetchPopularMeals()

        btnRandomMeal.setOnClickListener {
            fetchRandomMeal()
        }

        return view
    }

    private fun fetchCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getCategories()
                val categories = response.categories

                CoroutineScope(Dispatchers.Main).launch {
                    categoriesAdapter = CategoriesAdapter(categories, { category ->
                        showCategoryItems(category)
                    }, useSmallLayout = true)
                    rvCategories.layoutManager = GridLayoutManager(requireContext(), 4)
                    rvCategories.adapter = categoriesAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeFragment", "Error fetching categories: ${e.message}")
            }
        }
    }

    private fun fetchPopularMeals() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getPopularMeals()
                val meals = response.meals.shuffled().take(12) // Take 12 random meals

                CoroutineScope(Dispatchers.Main).launch {
                    popularMealsAdapter = PopularMealsAdapter(meals) { meal ->
                        Toast.makeText(requireContext(), "Clicked on ${meal.strMeal}", Toast.LENGTH_SHORT).show()
                    }
                    rvPopularMeals.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvPopularMeals.adapter = popularMealsAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeFragment", "Error fetching popular meals: ${e.message}")
            }
        }
    }

    private fun showCategoryItems(category: Category) {
        val fragment = CategoryItemsFragment().apply {
            arguments = Bundle().apply {
                putParcelable("category", category)
            }
        }
        parentFragmentManager.commit {
            replace(R.id.frag_host, fragment)
            addToBackStack(null)
        }
    }

    private fun fetchRandomMeal() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getRandomMeal()
                val meal = response.meals.firstOrNull()

                meal?.let {
                    CoroutineScope(Dispatchers.Main).launch {
                        mealName.text = it.strMeal
                        Glide.with(this@HomeFragment)
                            .load(it.strMealThumb)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(mealImage)
                        cardRandomMeal.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeFragment", "Error fetching random meal: ${e.message}")
            }
        }
    }
}
