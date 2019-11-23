package com.geonote.ui

import android.Manifest

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.geonote.BR
import com.geonote.GraphMainDirections
import com.geonote.R
import com.geonote.data.model.db.Note
import com.geonote.databinding.ActivityMainBinding
import com.geonote.ui.base.BaseActivity
import com.geonote.utils.RequestPermissions
import kotlinx.android.synthetic.main.activity_main.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttonMap.setOnClickListener {
            toMapActivity()
        }
        buttonMenu.setOnClickListener {
            toListFragment()
        }
    }

    override fun onStart() {
        if (requestPermission.ifHasPermissions()) Timber.e("Permissions")

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
    }

    fun toEditDetailFragment(note: Note) {
        val action = GraphMainDirections.actionToEditDetailfragment(note)
        mNavController!!.navigate(action)
    }

    fun toListFragment() {
        mNavController!!.navigate(R.id.listFragment)
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