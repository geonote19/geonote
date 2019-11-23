package com.geonote.ui.detail.mapDetails

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.FragmentDetailsMapBinding
import com.geonote.helper.MapHelper
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_details_map.*

class DetailsFragmentMap :
    BaseFragment<FragmentDetailsMapBinding, DetailFragmentMapViewModel, MainActivity>(),
    OnMapReadyCallback {

    override val mViewModelClass = DetailFragmentMapViewModel::class.java
    override val mLayoutId = R.layout.fragment_details_map
    override val mBindingVariable = BR.viewmodel

    private var mMapHelper: MapHelper? = null
    private val mCoordinates: MutableList<LatLng> = mutableListOf()

    val ZOOM_LEVEL = 10.0F

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapScreenDetails.let {
            lifecycle.addObserver(it)
            it.onCreate(savedInstanceState)
            it.getMapAsync(this)
        }

        btnSave.setOnClickListener {
            mMapHelper?.takeSnapshot(object : BitmapConsumer {
                override fun consume(bitmap: Bitmap) {
                    mActivity.mapBitmap = bitmap
                    mActivity.latlng = mCoordinates[0]
                    Navigation.findNavController(view).navigateUp()
                }
            })
        }

    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapScreenDetails.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapScreenDetails.onSaveInstanceState(outState)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        mMapHelper = MapHelper(googleMap, context!!)
        mMapHelper!!.chooseCoordinates(mCoordinates)
    }
}

interface BitmapConsumer {
    fun consume(bitmap: Bitmap)
}