package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.MainViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.MainViewState
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.MainViewState.State.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    var isConnectedToInternet: Boolean = false
        private set

    val actionBar: MaterialToolbar by lazy {
        findViewById(R.id.topAppBar)
    }

    private val navHostFragment : NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_YooxTest)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Set app bar
        actionBar.setupWithNavController(navController, appBarConfiguration)
        //actionBar.setLogo(R.drawable.ic_yoox_logo)
        viewModel.viewState.observe(this, observeViewState())
        viewModel.observeInternetConnection()
    }

    // Observes MainViewState, used for detecting internet connection change as of now
    private fun observeViewState() = Observer<MainViewState> { viewState ->
        when (viewState.state) {
            ERROR -> {
                makeToast(viewState.errorMessage)
            }
            CONNECTION_CHANGE -> {
                isConnectedToInternet = viewState.isConnected
                val currentFragment = getActiveFragment()
                // Handles BaseFragment connection change
                if (currentFragment is BaseFragment<*, *>) {
                    if (viewState.isConnected) {
                        currentFragment.onInternetConnected()
                    } else {
                        currentFragment.onInternetDisconnected()
                    }
                }
            }
            LOADING -> TODO()
        }
    }

    // Can be used by BaseFragment class and its children
    fun makeToast(toastMessage: String?) {
        Toast.makeText(
            this,
            toastMessage,
            Toast.LENGTH_LONG
        ).show()
    }

    // Returns the fragment that is currently on the screen
    private fun getActiveFragment(): Fragment? {
        return supportFragmentManager.fragments
            .first()?.childFragmentManager?.fragments?.get(0)
    }
}