package com.example.larecette.data.dataclasse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strArea: String?,
    val strInstructions: String?,
    val strItemDescription: String
) : Parcelable
