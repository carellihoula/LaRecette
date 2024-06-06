package com.example.larecette

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.larecette.R
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
        // Inflate the layout for this fragment
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
                    categoriesAdapter = CategoriesAdapter(categories)
                    rvCategories.layoutManager = LinearLayoutManager(requireContext())
                    rvCategories.adapter = categoriesAdapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
