package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration

/**
 * Created by Kadir Mert Özcan on 27-Mar-21.
 */

abstract class BaseFragment<DataBindingClass : ViewDataBinding, ViewModelClass : ViewModel>
    : Fragment() {

    private val mainActivity by lazy {
        activity as MainActivity
    }

    val navController by lazy {
        findNavController()
    }

    val appBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    private var rootView: View? = null

    val appCompatActivity: AppCompatActivity by lazy {
        activity as AppCompatActivity
    }

    // Previous internet connection status
    var previouslyDisconnected: Boolean = false

    // ViewDataBinding instance with the type parameter indicated by the child class
    lateinit var binding: DataBindingClass
        private set

    lateinit var viewModel: ViewModelClass
        private set

    // Layout res id for to inflate with data binding
    abstract val layoutId: Int


    // Must be set for providing type safe view model
    abstract val viewModelClass: Class<ViewModelClass>

    // Called just before onCreateView is finished
    abstract fun onViewBound()

    // Called just before onActivityCreated is finished
    abstract fun observeLiveData()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Applies type safe data binding
        binding = DataBindingUtil.inflate(
                inflater, layoutId, container, false) as DataBindingClass

        if (rootView == null) {
            rootView = binding.root
            onViewBound()
            return binding.root
        }
        onViewBound()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(viewModelClass)
        if (!getIsConnectedToInternet()) {
            previouslyDisconnected = true
        }

        // LiveData is observed by child classes in this method
        observeLiveData()
    }


    internal fun setSupportActionBar(hasNavigationButton: Boolean) {
        // To center the action bar logo (will probably break if more buttons are added)
        mainActivity.actionBar.run {
            if (hasNavigationButton) {
                setContentInsetsRelative(0,
                        contentInsetStartWithNavigation)
            } else {
                setContentInsetsRelative(0,
                        mainActivity.initialAppbarContentInsetEnd)
            }

        }
    }


    open fun onInternetConnected() {
        previouslyDisconnected = false
    }

    open fun onInternetDisconnected() {
        previouslyDisconnected = true
    }

    internal fun makeToast(toastMessage: String?) {
        mainActivity.makeToast(toastMessage)
    }


    internal fun getIsConnectedToInternet(): Boolean {
        return mainActivity.isConnectedToInternet
    }

}