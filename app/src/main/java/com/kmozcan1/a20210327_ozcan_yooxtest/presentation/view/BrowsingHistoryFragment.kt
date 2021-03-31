package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.BrowsingHistoryViewModel

class BrowsingHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = BrowsingHistoryFragment()
    }

    private lateinit var viewModel: BrowsingHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.browsing_history_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BrowsingHistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}