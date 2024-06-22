package com.vroomvroom.android.view.ui.browse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroomvroom.android.data.local.entity.merchant.SearchEntity
import com.vroomvroom.android.data.model.search.Search
import com.vroomvroom.android.repository.address.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val roomRepository: AddressRepository
) : ViewModel() {

    fun insertSearch(search: Search) {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

    fun deleteSearch(search: Search) {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

    fun getAllSearch(searches: (List<SearchEntity>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

}