package com.geonote.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.ActivityMainBinding
import com.geonote.ui.base.BaseActivity
import com.geonote.ui.detail.DetailFragmentArgs
import com.geonote.ui.list.ListFragmentDirections
import com.geonote.utils.RequestPermissions
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    private val LIST_PERMISSONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )


    private val requestPermission = RequestPermissions(this, LIST_PERMISSONS)

    override val mViewModelClass = MainActivityViewModel::class.java
    override val mLayoutId = R.layout.activity_main
    override val mBindingVariable = BR.viewmodel
    override val mNavHostId = R.id.navHostFragment


    override fun onStart() {
        if (requestPermission.ifHasPermissions()) Timber.e("Permissions")
        super.onStart()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestPermission.onRequestPermissionsResult(
            grantResults,
            R.string.text_request_permissions
        )
    }


    fun toDetailFragment(noteId: Long) {
        val action = ListFragmentDirections.actionToDetailfragment(noteId)
        mNavController!!.navigate(action)
    }

    fun toMapActivity() {
        mNavController!!.navigate(R.id.mapFragment)
    }
}