package com.geonote.core

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("stackless_fragment")
class StacklessFragmentNavigator(private val context: Context,
                                 private val manager: FragmentManager,
                                 private val containerId: Int) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(destination: Destination, args: Bundle?,
                          navOptions: NavOptions?, navigatorExtras: Navigator.Extras?): NavDestination? {
        if (manager.isStateSaved) {
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }
        val frag = manager.fragmentFactory.instantiate(context.classLoader, className)
        frag.arguments = args
        val ft = manager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        ft.replace(containerId, frag)
        ft.setPrimaryNavigationFragment(frag)

        if (navigatorExtras is Extras) {
            (navigatorExtras as? Extras)?.let {
                for ((key, value) in it.sharedElements) {
                    ft.addSharedElement(key, value)
                }
            }
        }

        ft.setReorderingAllowed(true)
        ft.commitNow()
        // The commit succeeded, update our view of the world
        return destination
    }
}