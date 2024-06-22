package com.vroomvroom.android.view.ui.orders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vroomvroom.android.R
import com.vroomvroom.android.data.model.merchant.Merchant
import com.vroomvroom.android.data.model.order.Order
import com.vroomvroom.android.databinding.ItemOrderBinding
import com.vroomvroom.android.utils.Utils.toUppercase

class OrdersDiffUtil: DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(
        oldItem: Order,
        newItem: Order
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Order,
        newItem: Order
    ): Boolean {
        return oldItem == newItem
    }

}

class OrderAdapter: ListAdapter<Order, OrderViewHolder>(OrdersDiffUtil()) {

    var onMerchantClicked: ((Merchant) -> Unit)? = null
    var onOrderClicked: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding: ItemOrderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_order,
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        val subtotal = order.products.sumOf { it.price.toDouble() }
        holder.binding.order = order
        val total = subtotal + order.deliveryFee
        holder.binding.apply {
            statusTv.text = order.status.name
                .toUppercase()
                .replace(",", " ")
            totalTv.text = holder.itemView.context.getString(
                R.string.peso, "%.2f".format(total))
            val orderProductAdapter = OrderProductAdapter(order.products)
            orderProductRv.adapter = orderProductAdapter

            orderMerchantLayout.setOnClickListener {
                onMerchantClicked?.invoke(order.merchant)
            }

            root.setOnClickListener {
                onOrderClicked?.invoke(order.id)
            }

            coverView.setOnClickListener {
                onOrderClicked?.invoke(order.id)
            }
        }
    }
}

class OrderViewHolder(val binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root)