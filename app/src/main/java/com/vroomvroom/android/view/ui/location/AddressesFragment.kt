package com.vroomvroom.android.view.ui.location

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vroomvroom.android.R
import com.vroomvroom.android.databinding.FragmentAddressesBinding
import com.vroomvroom.android.data.model.user.Address
import com.vroomvroom.android.utils.ClickType
import com.vroomvroom.android.view.resource.Resource
import com.vroomvroom.android.view.ui.base.BaseFragment
import com.vroomvroom.android.view.ui.location.adapter.AddressAdapter
import com.vroomvroom.android.view.ui.common.CommonAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddressesFragment : BaseFragment<FragmentAddressesBinding>(
    FragmentAddressesBinding::inflate
) {
    private val args: AddressesFragmentArgs by navArgs()
    private val adapter by lazy { AddressAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        binding.appBarLayout.toolbar.setupToolbar()

        binding.addressRv.adapter = adapter
        observeUserLocation()

        adapter.onAddressClicked = { address ->
            locationViewModel.clickedAddress = address
            locationViewModel.updateLocation(address.use())
        }

        adapter.onDeleteClicked = { address ->
            val currentAddressList = adapter.currentList
            val lastNotUseAddress = currentAddressList.last { !it.currentUse }
            if (address.currentUse) {
                locationViewModel.updateLocation(lastNotUseAddress.use())
            }
            if (currentAddressList.size > 1) {
                locationViewModel.deleteLocation(address)
            }
        }

        binding.btnAddAddress.setOnClickListener {
            navController.navigate(R.id.action_addressesFragment_to_mapsFragment)
        }

        if (args.orderId != null) {
            observeChangeAddress()
            binding.btnSave.visibility = View.VISIBLE
            adapter.currentUseAddress =  { address ->
                    binding.btnSave.setOnClickListener{
                        ordersViewModel
                            .updateOrderAddress(args.orderId.orEmpty(), address)
                }
            }
        }
    }

    private fun observeUserLocation() {
        locationViewModel.allAddress.observe(viewLifecycleOwner) { locations ->
            adapter.submitList(locations)
        }
    }

    private fun observeChangeAddress() {
        ordersViewModel.isAddressUpdated.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    handleSuccess()
                }
                is Resource.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    handleFailed()
                }
            }
        }
    }

    private fun handleSuccess() {
        val dialog = CommonAlertDialog(requireActivity())
        dialog.show(
            getString(R.string.updated_address_title),
            getString(R.string.updated_address_message),
            getString(R.string.ok),
            getString(R.string.ok),
            false
        ) { type ->
            when (type) {
                ClickType.POSITIVE -> {
                    dialog.dismiss()
                    navController.popBackStack()
                }
                ClickType.NEGATIVE -> Unit
            }
        }
    }

    private fun handleFailed() {
        val dialog = CommonAlertDialog(
            requireActivity()
        )
        dialog.show(
            getString(R.string.network_error),
            getString(R.string.network_error_message),
            getString(R.string.cancel),
            getString(R.string.retry)
        ) { type ->
            when (type) {
                ClickType.POSITIVE -> {
                    adapter.currentUseAddress = { address ->
                        ordersViewModel.updateOrderAddress(args.orderId.orEmpty(), address)
                    }
                    dialog.dismiss()
                }
                ClickType.NEGATIVE -> dialog.dismiss()
            }
        }
    }

    private fun Address.use(): Address {
        return Address(
            id = this.id,
            street = this.street,
            barangay = this.barangay,
            city = this.city,
            additionalInfo = this.additionalInfo,
            latitude = this.latitude,
            longitude = this.longitude,
            currentUse = true
        )
    }
}