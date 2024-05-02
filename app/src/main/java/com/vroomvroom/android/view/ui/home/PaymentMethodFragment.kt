package com.vroomvroom.android.view.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.vroomvroom.android.R
import com.vroomvroom.android.databinding.FragmentPaymentMethodBinding
import com.vroomvroom.android.view.ui.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class PaymentMethodFragment : BaseFragment<FragmentPaymentMethodBinding>(
    FragmentPaymentMethodBinding::inflate
) {

    private val isGCashActive by lazy { MutableLiveData(false) }
    private val isMayaActive by lazy { MutableLiveData(false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        binding.appBarLayout.toolbar.setupToolbar()

        observeViews()

        binding.apply {
            cashConstraint.setOnClickListener {
                mainActivityViewModel.paymentMethod.postValue("Cash On Delivery")
                navController.popBackStack()
            }
            gcashConstraint.setOnClickListener {
                mainActivityViewModel.paymentMethod.postValue("GCash")
                navController.popBackStack()
            }

            mayaConstraint.setOnClickListener {
                if (isMayaActive.value == true) {
                    arrowIndicator.setImageResource(R.drawable.ic_down_red_a30)
                } else {
                    arrowIndicator.setImageResource(R.drawable.ic_up_red_a30)
                }
                isMayaActive.postValue(isMayaActive.value?.not())
            }
        }
    }

    private fun observeViews() {
        isMayaActive.observe(viewLifecycleOwner) { isActive ->
            if (isActive) {
                binding.mayaActiveGroup.visibility = View.VISIBLE
            } else {
                binding.mayaActiveGroup.visibility = View.GONE
            }
        }
    }
}