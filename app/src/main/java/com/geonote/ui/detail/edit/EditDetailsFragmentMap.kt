package com.geonote.ui.detail

import android.os.Bundle
import com.geonote.databinding.FragmentDetailBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_details_map.*

class EditDetailsFragmentMap (val supportFragmentManager: SupportMapFragment) : BaseFragment<FragmentDetailBinding, EditDetailFragmentViewModel, MainActivity>(),
    OnMapReadyCallback {

    val ZOOM_LEVEL = 10.0F

    override val mViewModelClass: Class<EditDetailFragmentViewModel>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val mLayoutId: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val mBindingVariable: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapFragment = supportFragmentManager
            .mapScreenDetails as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        with(googleMap) {
            moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(LatLng(27.55, 53.90), ZOOM_LEVEL))
            addMarker(com.google.android.gms.maps.model.MarkerOptions().position(LatLng(27.55, 53.90)))
        }
    }
}