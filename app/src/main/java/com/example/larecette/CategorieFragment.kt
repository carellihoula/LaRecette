package com.example.larecette

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.larecette.adapters.CategoriesAdapter
import com.example.larecette.data.dataclasse.Category
import com.example.larecette.data.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategorieFragment : Fragment() {

    private lateinit var apiService: ApiService
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var rvCategories: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorie, container, false)
        rvCategories = view.findViewById(R.id.rv_categories)

        apiService = ApiService.create()

        fetchCategories()

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
                    }, useSmallLayout = false) // Set useSmallLayout to false for this fragment
                    rvCategories.layoutManager = GridLayoutManager(requireContext(), 3)
                    rvCategories.adapter = categoriesAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
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
}
