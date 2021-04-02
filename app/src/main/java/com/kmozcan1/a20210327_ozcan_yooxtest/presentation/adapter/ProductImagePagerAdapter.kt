package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ProductImagePagerViewBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.setImageUrl

/**
 * Created by Kadir Mert Özcan on 31-Mar-21.
 */
class ProductImagePagerAdapter (val imageUrlList: MutableList<String>) :
        RecyclerView.Adapter<ProductImagePagerAdapter.ProductImagePagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductImagePagerViewHolder {
        val binding = ProductImagePagerViewBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductImagePagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductImagePagerViewHolder, position: Int) {
        holder.bind(imageUrlList[position])
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    /** Adds the list of products image urls to the RecyclerView */
    fun addImageUrlList(imgUrlListResult: List<String>) {
        val startPosition = itemCount
        imageUrlList.addAll(imgUrlListResult)
        notifyItemRangeInserted(startPosition, imgUrlListResult.size)
    }

    /** Clears the RecyclerView data */
    fun clearImageUrlList() {
        val size = imageUrlList.size
        imageUrlList.clear()
        notifyItemRangeRemoved(0, size)
    }

    inner class ProductImagePagerViewHolder(
            private val binding: ProductImagePagerViewBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            binding.productDetailImageView.setImageUrl(imageUrl)
        }
    }
}