package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ProductSortBottomSheetBinding

/**
 * Created by Kadir Mert Özcan on 30-Mar-21.
 *
 * Bottom sheet dialog fragment where the product sort option bottoms are located
 */
class ProductSortBottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        fun newInstance() = ProductSortBottomSheetFragment()
    }

    lateinit var binding: ProductSortBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Sets sheet style
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = ProductSortBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This is used by the xml file to designate the
        // button onClick methods that are in ProductListFragment
        binding.productListFragment = parentFragment as ProductListFragment
    }

    override fun onStart() {
        super.onStart()
        // To fully extend the dialog on landscape orientation
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        binding.productListFragment?.sortDisabled = false
    }
}