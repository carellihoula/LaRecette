package com.example.larecette.data.dataclasse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemResponse(
    val meals: List<Meal>
):Parcelable

