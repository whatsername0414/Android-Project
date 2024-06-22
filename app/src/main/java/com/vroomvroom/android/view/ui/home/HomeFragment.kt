package com.vroomvroom.android.view.ui.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vroomvroom.android.R
import com.vroomvroom.android.databinding.FragmentHomeBinding
import com.vroomvroom.android.data.model.user.Address
import com.vroomvroom.android.utils.Constants.SCROLL_THRESHOLD
import com.vroomvroom.android.utils.Utils.safeNavigate
import com.vroomvroom.android.view.resource.Resource
import com.vroomvroom.android.view.ui.home.adapter.MerchantAdapter
import com.vroomvroom.android.view.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val merchantAdapter by lazy { MerchantAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUser()
        observeMerchants()
        observeUserLocation()
        observeRoomCartItem()
        shouldBackToTopObserver()
        setupAnimationListener()

        binding.merchantRv.adapter = merchantAdapter

        binding.addressLayout.setOnClickListener {
            findNavController().safeNavigate(
                HomeFragmentDirections.actionHomeFragmentToLocationBottomSheetFragment()
            )
        }

        binding.favorite.setOnClickListener {
            findNavController().safeNavigate(
                HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
            )
        }

        binding.cart.setOnClickListener {
            findNavController().safeNavigate(
                HomeFragmentDirections.actionHomeFragmentToCartBottomSheetFragment()
            )
        }

        binding.searchBar.setOnClickListener {
            findNavController().safeNavigate(
                HomeFragmentDirections.actionHomeFragmentToMerchantSearchFragment()
            )
        }

        binding.merchantRv.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            mainActivityViewModel.isHomeScrolled.postValue(scrollY > SCROLL_THRESHOLD)
        }

        merchantAdapter.onMerchantClicked = { merchant ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMerchantFragment(merchant.id)
            )
        }

        merchantAdapter.apply {
            onFavoriteClicked = { merchant, position, direction ->
                homeViewModel.updateFavorite(merchant.id)
                observeFavorite(this, merchant, position, direction)
            }
        }
    }

    private fun observeUser() {
        mainActivityViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.favorite.visibility = View.VISIBLE
                merchantAdapter.setUser(user)
                merchantAdapter.notifyDataSetChanged()
            } else {
                binding.favorite.visibility = View.GONE
                merchantAdapter.setUser(null)
                merchantAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun observeUserLocation() {
        locationViewModel.allAddress.observe(viewLifecycleOwner) { userLocation ->
            if (userLocation.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_homeFragment_to_locationFragment)
            } else {
                val location = userLocation.find { it.currentUse }
                location?.let { updateLocationTextView(it) }
            }
        }
    }
    private fun updateLocationTextView(address: Address) {
        binding.addressTv.text =
            address.street ?: getString(R.string.street_not_provided)
        binding.cityTv.text =
            address.city ?: getString(R.string.city_not_provided)
    }

    private fun observeMerchants() {
        mainViewModel.merchants.observe(viewLifecycleOwner) { response ->
            lifecycleScope.launch(Dispatchers.Main) {
                when (response) {
                    is Resource.Loading -> {
                        binding.apply {
                            commonNoticeLayout.hideNotice()
                            merchantsShimmerLayout.visibility = View.VISIBLE
                            merchantsShimmerLayout.startShimmer()
                        }
                    }
                    is Resource.Success -> {
                        val merchants = response.data
                        merchantAdapter.submitList(merchants)
                        binding.apply {
                            merchantsShimmerLayout.stopShimmer()
                            merchantsShimmerLayout.visibility = View.GONE
                        }
                    }
                    is Resource.Error -> {
                        binding.apply {
                            merchantsShimmerLayout.apply {
                                visibility = View.GONE
                                stopShimmer()
                            }
                            fetchProgress.visibility = View.GONE
                            commonNoticeLayout.showNetworkError(
                                listener = {
                                    val isLoggedIn = authViewModel.token.value != null
                                    mainViewModel.getMerchants(isLoggedIn = isLoggedIn)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun shouldBackToTopObserver() {
        mainActivityViewModel.shouldBackToTop.observe(viewLifecycleOwner) { shouldBackToTop ->
            if (shouldBackToTop) {
                binding.merchantRv.apply {
                    scrollBy(0, 1)
                    ObjectAnimator.ofInt(
                        this,
                        "scrollY",
                        binding.root.top
                    ).setDuration(400).start()
                }
                mainActivityViewModel.shouldBackToTop.postValue(false)
            }
        }
    }

    private fun observeRoomCartItem() {
        homeViewModel.cartItem.observe(viewLifecycleOwner) { items ->
            if (items.isNullOrEmpty()) {
                binding.cartCounter.visibility = View.GONE
            } else {
                binding.cartCounter.apply {
                    text = "${items.size}"
                    visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupAnimationListener() {
        val isLoggedIn = authViewModel.token.value != null
        if (view?.animation != null) {
            view?.animation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    if (mainActivityViewModel.shouldFetchMerchants) {
                        mainViewModel.getMerchants(isLoggedIn = isLoggedIn)
                        mainActivityViewModel.shouldFetchMerchants = false
                        return
                    }
                    if (mainViewModel.merchants.value == null) {
                        mainViewModel.getMerchants(isLoggedIn = isLoggedIn)
                    }
                }
                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
        } else {
            if (mainActivityViewModel.shouldFetchMerchants) {
                mainViewModel.getMerchants(isLoggedIn = isLoggedIn)
                mainActivityViewModel.shouldFetchMerchants = false
                return
            }
            if (mainViewModel.merchants.value == null) {
                mainViewModel.getMerchants(isLoggedIn = isLoggedIn)
            }
        }
    }

    companion object {
        const val MAIN_CATEGORY = "main"
    }
}