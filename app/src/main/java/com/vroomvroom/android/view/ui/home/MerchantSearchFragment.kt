package com.vroomvroom.android.view.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vroomvroom.android.R
import com.vroomvroom.android.databinding.FragmentMerchantSearchBinding
import com.vroomvroom.android.data.local.entity.merchant.SearchEntity
import com.vroomvroom.android.data.model.search.Search
import com.vroomvroom.android.utils.ClickType
import com.vroomvroom.android.utils.Utils.showSoftKeyboard
import com.vroomvroom.android.view.resource.Resource
import com.vroomvroom.android.view.ui.base.BaseFragment
import com.vroomvroom.android.view.ui.browse.adapter.SearchAdapter
import com.vroomvroom.android.view.ui.home.adapter.MerchantAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.ArrayList

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MerchantSearchFragment : BaseFragment<FragmentMerchantSearchBinding>(
    FragmentMerchantSearchBinding::inflate
) {
    private val searchAdapter by lazy { SearchAdapter() }
    private val merchantAdapter by lazy { MerchantAdapter() }
    private var currentSearchTerm: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchView()
        observeMerchantsLiveData()

        binding.merchantRv.adapter = merchantAdapter.apply { setUser(user) }
        binding.searchRv.adapter = searchAdapter

        observeSearches()

        searchAdapter.listener = { type, search, _ ->
            when (type) {
                ClickType.NEGATIVE -> {
                    val searchTerm = search.searchTerm
                    currentSearchTerm = searchTerm
                    binding.searchView.setQuery(searchTerm, false)
                    binding.title.visibility = View.GONE
                    binding.merchantsShimmerLayout.visibility = View.GONE
                    binding.merchantsShimmerLayout.stopShimmer()
                    binding.searchView.clearFocus()
                    mainViewModel.getMerchants(null, searchTerm)
                }
                ClickType.POSITIVE -> {
                    homeViewModel.deleteSearch(search)
                }
            }
        }

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.commonNoticeLayout.hideNotice()
                binding.recentSearchLayout.visibility = View.VISIBLE
                showSoftKeyboard(binding.searchView)
            } else {
                binding.recentSearchLayout.visibility = View.GONE
            }
        }

        merchantAdapter.apply {
            onFavoriteClicked = { merchant, position, direction ->
                homeViewModel.updateFavorite(merchant.id)
                observeFavorite(this, merchant, position, direction)
            }
        }

        merchantAdapter.onMerchantClicked = { merchant ->
            findNavController().navigate(
                FavoriteFragmentDirections.actionGlobalToMerchantFragment(merchant.id)
            )
        }

        binding.btnBack.setOnClickListener {
            if (binding.searchView.hasFocus() && merchantAdapter.itemCount > 0) {
                binding.searchView.clearFocus()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private fun initSearchView() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if ((query?.length ?: 0) < 3) {
                        showShortToast(R.string.search_minimum)
                        return false
                    }
                    currentSearchTerm = query
                    mainViewModel.getMerchants(null, query)
                    clearFocus()
                    homeViewModel.insertSearch(query ?: "")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun observeMerchantsLiveData() {
        mainViewModel.merchants.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.commonNoticeLayout.hideNotice()
                    binding.merchantsShimmerLayout.visibility = View.VISIBLE
                    binding.merchantsShimmerLayout.startShimmer()
                }
                is Resource.Success -> {
                    val merchants = response.data
                    if (merchants.isEmpty()) {
                        binding.searchGroup.visibility = View.GONE
                        binding.commonNoticeLayout.showNotice(
                            R.drawable.ic_empty_result,
                            R.string.empty_result,
                            R.string.empty_result_message,
                            currentSearchTerm,
                            null,
                            false
                        ) {}
                    } else {
                        binding.searchGroup.visibility = View.VISIBLE
                        binding.title.apply {
                            val resultSize = merchants.size
                            text = if(resultSize > 1)
                                getString(R.string.search_results, resultSize, currentSearchTerm)
                            else getString(R.string.search_result, currentSearchTerm)
                        }
                        merchantAdapter.submitList(merchants.toMutableList())
                    }
                    binding.merchantsShimmerLayout.visibility = View.GONE
                    binding.merchantsShimmerLayout.stopShimmer()
                }
                is Resource.Error -> {
                    binding.searchGroup.visibility = View.GONE
                    binding.merchantsShimmerLayout.visibility = View.GONE
                    binding.merchantsShimmerLayout.stopShimmer()
                    binding.commonNoticeLayout.showNetworkError(
                        listener = {
                            mainViewModel.getMerchants(null, currentSearchTerm)
                        }
                    )
                }
            }
        }
    }

    private fun observeSearches() {
        homeViewModel.searches.observe(viewLifecycleOwner) { searches ->
            if (searches.isNotEmpty()) {
                searchAdapter.submitList(searches)
            }
        }
    }
}