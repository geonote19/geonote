package com.geonote.ui

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.geonote.BR
import com.geonote.GraphMainDirections
import com.geonote.R
import com.geonote.data.model.db.Note
import com.geonote.databinding.ActivityMainBinding
import com.geonote.ui.base.BaseActivity
import com.geonote.ui.list.ListFragment
import com.geonote.ui.mapAllNotes.AllNotesFragmentBehavior
import com.geonote.utils.CustomViewOutlineProvider
import com.geonote.utils.RequestPermissions
import com.geonote.utils.applyDefaultOutlineProvider
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottomsheet_main.*
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
    var mapBitmap: Bitmap? = null
    var latlng: LatLng? = null
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        setupNavigationBar()
        super.onCreate(savedInstanceState)
        buttonMap.setOnClickListener {
            if (currentFragment !is AllNotesFragmentBehavior) {
                toMapActivity()
            }

        }
        buttonMenu.setOnClickListener {
            if (currentFragment !is ListFragment) {
                toListFragment()
            }
        }
//        buttonAdd.setOnClickListener {

//            var newNote = Note(
//                0,
//                "",
//                "",
//                "",
//                53.899604,
//                27.557117,
//                100,
//                Date().addDays(-1).time,
//                Date().addDays(2).time,
//                Color.RED
//            )
//            toEditDetailFragment(newNote)
//        }
        setupBottomSheetBehavior()
        buttonPlaceMarker.setOnClickListener {
            Toast.makeText(this, R.string.place_marker_please, Toast.LENGTH_LONG).show()
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            val note = Note(
                0, textTitle.text.toString(), textDescription.text.toString(),
                "", 0.0, 0.0, 100, 0,
                System.currentTimeMillis() + 1_000_000, Color.RED
            )
            if (currentFragment is AllNotesFragmentBehavior) {
                (currentFragment as AllNotesFragmentBehavior).setNoteForSaving(note)
            } else toMapFragment(note)
        }
    }

    override fun onStart() {
        if (requestPermission.ifHasPermissions()) Timber.e("Permissions")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.colorToolbar)
            window.navigationBarColor = Color.WHITE
        }
        super.onStart()
    }

    private fun setupBottomSheetBehavior() {
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetMain)
        bottomSheetMain.applyDefaultOutlineProvider(CustomViewOutlineProvider.RoundedArea.TOP)
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        buttonAdd.setOnClickListener {
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
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


    fun toMapFragment(note: Note) {
        val action = GraphMainDirections.actionToMapFragment(note)
        mNavController?.navigate(action)
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

    fun setupNavigationBar() {
        if (Build.VERSION.SDK_INT >= 26) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}