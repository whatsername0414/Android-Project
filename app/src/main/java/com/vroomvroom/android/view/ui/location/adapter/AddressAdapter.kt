package com.vroomvroom.android.view.ui.location.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vroomvroom.android.R
import com.vroomvroom.android.data.model.user.Address
import com.vroomvroom.android.databinding.ItemAddressBinding

class AddressDiffUtil: DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(
        oldItem: Address,
        newItem: Address
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Address,
        newItem: Address
    ): Boolean {
        return oldItem == newItem
    }
}

class AddressAdapter: ListAdapter<Address, AddressViewHolder>(AddressDiffUtil()) {

    var currentUseAddress: ((Address) -> Unit)? = null
    var onAddressClicked: ((Address) -> Unit)? = null
    var onDeleteClicked: ((Address) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding: ItemAddressBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_address,
            parent,
            false
        )
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = getItem(position)
        holder.binding.address = address

        if (address.currentUse) {
            currentUseAddress?.invoke(address)
        }

        holder.binding.root.setOnClickListener {
            onAddressClicked?.invoke(address)
        }

        if (itemCount == 1) {
            holder.binding.delete.visibility = View.GONE
        }

        holder.binding.delete.setOnClickListener {
            onDeleteClicked?.invoke(address)
        }
    }
}

class AddressViewHolder(val binding: ItemAddressBinding): RecyclerView.ViewHolder(binding.root)
