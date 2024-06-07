package com.example.larecette.data.dataclasse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class MealResponse(
    val meals: List<MealDetail>
)
