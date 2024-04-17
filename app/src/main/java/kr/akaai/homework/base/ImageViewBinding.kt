package kr.akaai.homework.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("url")
fun setImageUrl(view: ImageView, url: String) = Glide.with(view).load(url).into(view)