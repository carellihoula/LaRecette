package com.example.larecette.data.retrofit

import com.example.larecette.data.dataclasse.CategoriesResponse
import com.example.larecette.data.dataclasse.ItemResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse
    @GET("filter.php")
    suspend fun getCategoryItems(@Query("c") categoryName: String): ItemResponse

    companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}


