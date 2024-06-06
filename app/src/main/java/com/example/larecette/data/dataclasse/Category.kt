package com.example.larecette.data.dataclasse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val strCategory: String, val strCategoryThumb: String, val strCategoryDescription: String): Parcelable


