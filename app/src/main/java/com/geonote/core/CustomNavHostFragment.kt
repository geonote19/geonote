package com.geonote.core

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class CustomNavHostFragment : NavHostFragment() {
    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        context?.let {
            navController.navigatorProvider.addNavigator(StacklessFragmentNavigator(it, childFragmentManager, id))
        }
    }
}