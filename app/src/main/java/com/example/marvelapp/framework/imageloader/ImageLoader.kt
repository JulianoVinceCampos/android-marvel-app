package com.example.marvelapp.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {

    fun load(imageview: ImageView, imageUrl: String, @DrawableRes fallback: Int)

}