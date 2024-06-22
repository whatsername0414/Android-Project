package com.vroomvroom.android.view.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.vroomvroom.android.R
import com.vroomvroom.android.databinding.FragmentMerchantInfoBinding
import com.vroomvroom.android.utils.Constants
import com.vroomvroom.android.utils.Utils
import com.vroomvroom.android.utils.Utils.setMap
import com.vroomvroom.android.view.ui.base.BaseFragment
import com.vroomvroom.android.view.ui.home.adapter.ReviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MerchantInfoFragment : BaseFragment<FragmentMerchantInfoBinding>(
    FragmentMerchantInfoBinding::inflate
), OnMapReadyCallback {

    private val adapter by lazy { ReviewAdapter() }
    private var map: GoogleMap? = null
    private var mapView: MapView? = null

    private lateinit var merchantLocation: LatLng

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.userLocationMapView
        initGoogleMap(savedInstanceState)
        observeAddress()
        navController = findNavController()
        binding.appBarLayout.apply {
            toolbar.setupToolbar()
            toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close_maroon)
        }

        binding.reviewRv.adapter = adapter
        val merchant = mainActivityViewModel.merchant
        binding.merchant = merchant
        adapter.submitList(merchant.reviews)
        binding.ratingTextView.text = if (merchant.ratings.isNaN()) "No Reviews" else
            "${merchant.ratings} (${merchant.reviews.size} ${if (merchant.reviews.size == 1) "Review"
            else "Reviews"})"
        binding.scheduleTextView.text = getString(
            R.string.time,
            Utils.timeFormatter(merchant.opening),
            Utils.timeFormatter(merchant.closing)
        )
        merchantLocation = LatLng(
            merchant.address[0],
            merchant.address[1]
        )
        locationViewModel.getAddress(merchantLocation)
    }

    @SuppressLint("SetTextI18n")
    private fun observeAddress() {
        locationViewModel.address.observe(viewLifecycleOwner) { res ->
            res?.let {
                binding.locationDetailTextView.text = if (res.thoroughfare.isNullOrEmpty()) res.locality
                    else "${it.thoroughfare}, ${it.locality}"
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setMap(requireContext(), merchantLocation)
    }

    private fun initGoogleMap(savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constants.MAPVIEW_BUNDLE_KEY)
        }
        mapView?.onCreate(mapViewBundle)
        mapView?.getMapAsync(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }
    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }
    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }
}