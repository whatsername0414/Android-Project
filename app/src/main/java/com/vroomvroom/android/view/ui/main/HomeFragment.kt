package com.vroomvroom.android.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vroomvroom.android.databinding.FragmentHomeBinding
import com.vroomvroom.android.view.adapter.CategoryAdapter
import com.vroomvroom.android.view.adapter.RestaurantAdapter
import com.vroomvroom.android.view.state.ViewState
import com.vroomvroom.android.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val categoryAdapter by lazy { CategoryAdapter() }
    private val restaurantAdapter by lazy { RestaurantAdapter() }
    private val viewModel by viewModels<DataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoriesRv.adapter = categoryAdapter
        binding.restaurantsRv.adapter = restaurantAdapter

        viewModel.queryHomeData()
        observeLiveData()

        categoryAdapter.onCategoryClicked = { category ->
            category.let {
                if (category?.name != null) {
                    viewModel.queryRestaurantByCategory(category.name)
                } else {
                    viewModel.queryRestaurantByCategory("")
                }
                observeRestaurantLiveData()
            }
        }
    }

    private fun observeLiveData() {
        viewModel.homeData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ViewState.Loading -> {
                    binding.categoriesRv.visibility = View.GONE
                    binding.fetchProgress.visibility = View.VISIBLE
                }
                is ViewState.Success -> {
                    if (response.value?.data == null) {
                        categoryAdapter.submitList(emptyList())
                        restaurantAdapter.submitList(emptyList())
                        binding.fetchProgress.visibility = View.GONE
                        binding.categoriesRv.visibility = View.GONE
                        binding.restaurantsRv.visibility = View.GONE
                        binding.homeEmptyText.visibility = View.VISIBLE
                    } else {
                        binding.categoriesRv.visibility = View.VISIBLE
                        binding.restaurantsRv.visibility = View.VISIBLE
                        binding.homeEmptyText.visibility = View.GONE
                    }
                    val category = response.value?.data?.getCategories
                    val restaurant = response.value?.data?.getRestaurants
                    categoryAdapter.submitList(category)
                    restaurantAdapter.submitList(restaurant)
                    binding.fetchProgress.visibility = View.GONE
                }
                is ViewState.Error -> {
                    categoryAdapter.submitList(emptyList())
                    restaurantAdapter.submitList(emptyList())
                    binding.fetchProgress.visibility = View.GONE
                    binding.categoriesRv.visibility = View.GONE
                    binding.restaurantsRv.visibility = View.GONE
                    binding.homeEmptyText.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeRestaurantLiveData() {
        viewModel.homeData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ViewState.Loading -> {
                    binding.categoriesRv.visibility = View.VISIBLE
                    binding.restaurantsRv.visibility = View.VISIBLE
                    binding.fetchProgress.visibility = View.VISIBLE
                }
                is ViewState.Success -> {
                    if (response.value?.data == null) {
                        restaurantAdapter.submitList(emptyList())
                        binding.fetchProgress.visibility = View.GONE
                        binding.restaurantsRv.visibility = View.GONE
                        binding.homeEmptyText.visibility = View.VISIBLE
                    } else {
                        binding.homeEmptyText.visibility = View.GONE
                    }
                    val restaurant = response.value?.data?.getRestaurants
                    restaurantAdapter.submitList(restaurant)
                    binding.fetchProgress.visibility = View.GONE
                }
                is ViewState.Error -> {
                    categoryAdapter.submitList(emptyList())
                    restaurantAdapter.submitList(emptyList())
                    binding.fetchProgress.visibility = View.GONE
                    binding.categoriesRv.visibility = View.GONE
                    binding.restaurantsRv.visibility = View.GONE
                    binding.homeEmptyText.visibility = View.VISIBLE
                }
            }
        }
    }
}