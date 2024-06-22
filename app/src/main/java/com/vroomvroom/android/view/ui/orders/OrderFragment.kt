package com.vroomvroom.android.view.ui.orders

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.vroomvroom.android.data.enums.OrderStatus
import com.vroomvroom.android.databinding.FragmentOrderBinding
import com.vroomvroom.android.utils.Utils.safeNavigate
import com.vroomvroom.android.view.resource.Resource
import com.vroomvroom.android.view.ui.base.BaseFragment
import com.vroomvroom.android.view.ui.orders.adapter.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(
    FragmentOrderBinding::inflate
) {

    private val orderAdapter by lazy { OrderAdapter() }
    private lateinit var orderStatus: OrderStatus

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderStatus = arguments?.getParcelable(STATUS) ?: OrderStatus.PENDING
        binding.ordersRv.adapter = orderAdapter
        observeOrdersByStatusLiveData()
        observeIsRefreshed()
        ordersViewModel.getOrdersByStatus(orderStatus)

        orderAdapter.onMerchantClicked = { merchant ->
            if (merchant.id.isNotBlank()) {
                findNavController().safeNavigate(
                    OrdersFragmentDirections.actionGlobalToMerchantFragment(merchant.id)
                )
            }
        }
        orderAdapter.onOrderClicked = { orderId ->
            findNavController().navigate(
                OrdersFragmentDirections.actionOrdersFragmentToOrderDetailFragment(orderId)
            )
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            ordersViewModel.getOrdersByStatus(orderStatus)
            mainActivityViewModel.isRefreshed.postValue(true)
        }
    }

    private fun observeOrdersByStatusLiveData() {
        ordersViewModel.orders.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.ordersRv.visibility = View.GONE
                    binding.commonNoticeLayout.hideNotice()
                    binding.shimmerLayout.startShimmer()
                    binding.shimmerLayout.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    val orders = response.data
                    if (orders.isEmpty()) {
                        orderAdapter.submitList(emptyList())
                        binding.commonNoticeLayout.showEmptyOrder {
                            findNavController().popBackStack() }
                    } else {
                        orderAdapter.submitList(orders)
                        binding.ordersRv.visibility = View.VISIBLE
                    }
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is Resource.Error -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    binding.ordersRv.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.commonNoticeLayout.showNetworkError(
                        listener = {
                            ordersViewModel.getOrdersByStatus(orderStatus)
                        }
                    )
                }
            }
        }
    }

    private fun observeIsRefreshed() {
        mainActivityViewModel.isRefreshed.observe(viewLifecycleOwner) { refreshed ->
            if (refreshed) {
                ordersViewModel.getOrdersByStatus(orderStatus)
            }
        }
    }

    companion object {
        private const val STATUS = "status"

        /**
         * current won't use anymore
         */
        fun newInstance(
            orderStatus: OrderStatus
        ): OrderFragment {
            val fragment = OrderFragment()
            val bundle = Bundle()
            bundle.putParcelable(STATUS, orderStatus)
            fragment.arguments = bundle
            return fragment
        }
    }
}