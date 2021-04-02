package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.SizeButtonListItemBinding

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 */
class SizeButtonListAdapter(
        private val sizeList: MutableList<String>,
        private val callbackListener: CallbackListener
) :
        RecyclerView.Adapter<SizeButtonListAdapter.SizeButtonListViewHolder>() {

    lateinit var recyclerView: RecyclerView
    var selectedButtonPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SizeButtonListViewHolder {
        val binding = SizeButtonListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return SizeButtonListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SizeButtonListViewHolder, position: Int) {
        holder.setup(sizeList[position], position)
    }

    override fun onAttachedToRecyclerView(recView: RecyclerView) {
        super.onAttachedToRecyclerView(recView)
        recyclerView = recView
    }


    override fun getItemCount(): Int {
        return sizeList.size
    }

    /** Adds the list of sizes to the RecyclerView */
    fun addSizeList(sizeCodeListItems: List<String>) {
        val startPosition = itemCount
        sizeList.addAll(sizeCodeListItems)
        notifyItemRangeInserted(startPosition, sizeCodeListItems.size)
    }

    /** Clears the RecyclerView data */
    fun clearSizeList() {
        val size = sizeList.size
        sizeList.clear()
        notifyItemRangeRemoved(0, size)
    }

    /** Removes the stroke of the currently selected button */
    fun unSelectButton() {
        val view = recyclerView
                .findViewHolderForAdapterPosition(selectedButtonPosition)?.itemView
        val button = view?.findViewById<MaterialButton>(R.id.sizeButton)
        button?.let {
            button.strokeColor = ContextCompat.getColorStateList(
                    button.context, android.R.color.transparent)
        }
        selectedButtonPosition = -1
    }

    /** Disables buttons that are unavailable to the selected color variant */
    fun refreshButtons(availableButtonList: MutableList<String?>) {
        for (i in sizeList.indices) {
            val view = recyclerView
                    .findViewHolderForAdapterPosition(i)?.itemView
            val button = view?.findViewById<MaterialButton>(R.id.sizeButton)
            if (!availableButtonList.contains(sizeList[i])) {
                // set text color to gray to indicate that it is disabled and make it not clickable
                button?.apply {
                    setTextColor(ContextCompat.getColorStateList(
                            context, R.color.gray))
                    isClickable = false
                }
            } else {
                // set text color to black to indicate that it is enabled and make it clickable
                button?.apply {
                    setTextColor(ContextCompat.getColorStateList(
                            context, R.color.black))
                    isClickable = true

                }
            }
        }
    }

    inner class SizeButtonListViewHolder(
            private val binding: SizeButtonListItemBinding
    ) :
            RecyclerView.ViewHolder(binding.root) {

        fun setup(sizeName: String, position: Int) {
            binding.sizeButton.run {
                // Sets the button text
                text = sizeName

                binding.sizeButton.setOnClickListener {
                    if (selectedButtonPosition != position) {
                        // remove stroke from the unselected button
                        val view = recyclerView
                                .findViewHolderForAdapterPosition(selectedButtonPosition)?.itemView
                        val button = view?.findViewById<MaterialButton>(R.id.sizeButton)
                        button?.strokeColor =
                                ContextCompat.getColorStateList(
                                        context,
                                        android.R.color.transparent
                                )

                        // add black stroke to the selected button (or white if the size is black)
                        binding.sizeButton.strokeColor =
                                ContextCompat.getColorStateList(context, R.color.black)

                        selectedButtonPosition = position
                        callbackListener.onSizeButtonClick()
                    }
                }
            }
        }
    }


    /** Listener interface to make callbacks to the view that uses SizeButtonView */
    interface CallbackListener {
        fun onSizeButtonClick()
    }
}