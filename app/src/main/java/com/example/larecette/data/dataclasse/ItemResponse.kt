package com.example.larecette.data.dataclasse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ItemResponse(
    val meals: List<Meal>
)

