package com.vroomvroom.android.utils

import android.content.Intent
import com.vroomvroom.android.data.model.merchant.Option
import com.vroomvroom.android.data.model.merchant.Product
import com.vroomvroom.android.view.resource.Resource


interface OnProductClickListener {
    fun onProductClick(product: Product)
}

interface OnOptionClickListener {
    fun onOptionClick(option: Option, optionType: String)
}

interface SmsBroadcastReceiverListener {
    fun onIntent(intent: Resource<Intent>)
}