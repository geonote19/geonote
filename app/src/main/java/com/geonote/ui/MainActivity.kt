package com.geonote.ui

import android.Manifest
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.geonote.BR
import com.geonote.CustomDialog
import com.geonote.GraphMainDirections
import com.geonote.R
import com.geonote.databinding.ActivityMainBinding
import com.geonote.ui.base.BaseActivity
import com.geonote.ui.detail.DetailFragmentArgs
import com.geonote.ui.detail.DetailFragmentDirections
import com.geonote.ui.detail.EditDetailFragmentDirections
import com.geonote.ui.list.ListFragmentDirections
import com.geonote.utils.RequestPermissions
import kotlinx.android.synthetic.main.custom_dialog_note.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    private val LIST_PERMISSONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR
    )

    private val requestPermission = RequestPermissions(this, LIST_PERMISSONS)

    override val mViewModelClass = MainActivityViewModel::class.java
    override val mLayoutId = R.layout.activity_main
    override val mBindingVariable = BR.viewmodel
    override val mNavHostId = R.id.navHostFragment


    override fun onStart() {
        if (requestPermission.ifHasPermissions()) Timber.e("Permissions")
        setSupportActionBar(toolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.colorToolbar)
        }
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


    fun toMapDetailFragment(noteId: Long) {
        val action = EditDetailFragmentDirections.mapPreview(noteId)
        mNavController?.navigate(action)
    }

    fun toEditDetailFragment(noteId: Long) {
        val action = GraphMainDirections.actionToEditDetailfragment(noteId)
        mNavController!!.navigate(action)
    }

    fun toDetailFragment(noteId: Long) {
        val action = GraphMainDirections.actionToDetailfragment(noteId)
        mNavController!!.navigate(action)
    }

    fun toMapActivity() {
        mNavController!!.navigate(R.id.mapFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}