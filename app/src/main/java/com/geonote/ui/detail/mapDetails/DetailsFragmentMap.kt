package com.geonote.ui.detail.mapDetails

import android.os.Bundle
import android.view.View
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.FragmentDetailsMapBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_details_map.*

class DetailsFragmentMap : BaseFragment<FragmentDetailsMapBinding, DetailFragmentMapViewModel, MainActivity>(),
    OnMapReadyCallback {

    override val mViewModelClass = DetailFragmentMapViewModel::class.java
    override val mLayoutId = R.layout.fragment_details_map
    override val mBindingVariable = BR.viewmodel

    val ZOOM_LEVEL = 10.0F

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapScreenDetails.let {
            lifecycle.addObserver(it)
            it.onCreate(savedInstanceState)
            it.getMapAsync(this)
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
        with(googleMap) {
            moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(LatLng(27.55, 53.90), ZOOM_LEVEL))
            addMarker(com.google.android.gms.maps.model.MarkerOptions().position(LatLng(27.55, 53.90)))
        }
    }
}