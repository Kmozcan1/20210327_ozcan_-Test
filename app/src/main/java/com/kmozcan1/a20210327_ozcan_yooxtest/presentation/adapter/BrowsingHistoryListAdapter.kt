package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.BrowsingHistoryListItemBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.BrowsingHistoryUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.setImageUrl

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 */
class BrowsingHistoryListAdapter (
        private val browsingHistoryList: MutableList<BrowsingHistoryUiModel>,
        private val listener: (BrowsingHistoryUiModel) -> Unit) :
        RecyclerView.Adapter<BrowsingHistoryListAdapter.BrowsingHistoryListItemViewHolder>() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            BrowsingHistoryListItemViewHolder {
        // Inflate with data binding
        val binding = BrowsingHistoryListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return BrowsingHistoryListItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: BrowsingHistoryListItemViewHolder, position: Int) {
        holder.bind(browsingHistoryList[position])
    }

    override fun onAttachedToRecyclerView(recView: RecyclerView) {
        super.onAttachedToRecyclerView(recView)
        recyclerView = recView
    }

    override fun getItemCount(): Int {
        return browsingHistoryList.size
    }

    /** Adds the browsing history list to the RecyclerView */
    fun addBrowsingHistoryList(browsingHistoryListResult: List<BrowsingHistoryUiModel>) {
        val startPosition = itemCount
        browsingHistoryList.addAll(browsingHistoryListResult)
        notifyItemRangeInserted(startPosition, browsingHistoryListResult.size)
    }

    /** Clears the RecyclerView data */
    fun clearBrowsingHistoryList() {
        val size = browsingHistoryList.size
        browsingHistoryList.clear()
        notifyItemRangeRemoved(0, size)
    }

    inner class BrowsingHistoryListItemViewHolder(
            private val binding: BrowsingHistoryListItemBinding,
            val listener: (BrowsingHistoryUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(browsingHistory: BrowsingHistoryUiModel) {
            binding.run {
                browsingHistoryBrandTextView.text = browsingHistory.brand
                browsingHistoryCategoryTextView.text = browsingHistory.category
                browsingHistoryImageView.setImageUrl(browsingHistory.imageUrl)
                root.setOnClickListener{
                    listener(browsingHistory)
                }
            }
        }
    }
}