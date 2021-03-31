package com.kmozcan1.a20210327_ozcan_yooxtest.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Created by Kadir Mert Özcan on 28-Mar-21.
 *
 * Utilities class for extensions and binding adapters
 */

// SetAdapter extension for better code readability
fun RecyclerView.setRecyclerView(
        layoutManager: RecyclerView.LayoutManager,
        adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}

// Extension function for binding adapter that loads image url with glide
@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    // Progress animation to show while the image is loading
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 80f
    circularProgressDrawable.start()

    Glide.with(this.context)
        .load(url)
        //.placeholder(circularProgressDrawable)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
