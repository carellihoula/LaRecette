package com.example.larecette

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.larecette.adapters.CategoryItemsAdapter
import com.example.larecette.data.dataclasse.Category
import com.example.larecette.data.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.TextView
import androidx.fragment.app.commit
import com.example.larecette.data.dataclasse.Meal

class CategoryItemsFragment : Fragment() {

    private lateinit var apiService: ApiService
    private lateinit var categoryItemsAdapter: CategoryItemsAdapter
    private lateinit var rvCategoryItems: RecyclerView
    private lateinit var categoryTitle: TextView
    private lateinit var itemCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category_items, container, false)
        rvCategoryItems = view.findViewById(R.id.rv_category_items)
        categoryTitle = view.findViewById(R.id.categoryTitle)
        itemCount = view.findViewById(R.id.itemCount)

        apiService = ApiService.create()

        val category = arguments?.getParcelable<Category>("category")

        category?.let {
            categoryTitle.text = it.strCategory
            fetchCategoryItems(it.strCategory)
        }

        return view
    }

    private fun fetchCategoryItems(categoryName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getCategoryItems(categoryName)
                val items = response.meals

                CoroutineScope(Dispatchers.Main).launch {
                    itemCount.text = "${items.size} items"
                    categoryItemsAdapter = CategoryItemsAdapter(items) { meal ->
                        showMealDetails(meal)
                    }
                    rvCategoryItems.layoutManager = GridLayoutManager(requireContext(), 3)
                    rvCategoryItems.adapter = categoryItemsAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
   private fun showMealDetails(meal: Meal) {
       val fragment = MealDetailFragment().apply {
           arguments = Bundle().apply {
               putString("mealId", meal.idMeal)
           }
       }
        parentFragmentManager.commit {
            replace(R.id.frag_host, fragment)
            addToBackStack(null)
        }
    }

}
