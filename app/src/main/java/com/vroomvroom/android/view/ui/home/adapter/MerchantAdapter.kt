package com.vroomvroom.android.view.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vroomvroom.android.R
import com.vroomvroom.android.databinding.ItemMerchantBinding
import com.vroomvroom.android.data.local.entity.user.UserEntity
import com.vroomvroom.android.data.model.merchant.Merchant
import com.vroomvroom.android.utils.Constants.ADD_TO_FAVORITES
import com.vroomvroom.android.utils.Constants.REMOVE_FROM_FAVORITES
import com.vroomvroom.android.utils.Utils.getImageUrl
import com.vroomvroom.android.utils.Utils.setSafeOnClickListener
import com.vroomvroom.android.utils.Utils.timeFormatter

class MerchantDiffUtil : DiffUtil.ItemCallback<Merchant>() {
    override fun areItemsTheSame(oldItem: Merchant, newItem: Merchant): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Merchant, newItem: Merchant): Boolean {
        return oldItem == newItem
    }

}

class MerchantAdapter: ListAdapter<Merchant, MerchantViewHolder>(MerchantDiffUtil()) {

    private var currentUserEntity: UserEntity? = null
    var onMerchantClicked: ((Merchant) -> Unit)? = null
    var onFavoriteClicked: ((
        Merchant,
        position: Int,
        direction: Int
    ) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchantViewHolder {
        val binding: ItemMerchantBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_merchant,
            parent,
            false,

        )
        return MerchantViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MerchantViewHolder, position: Int) {
        val merchant = getItem(position)
        holder.binding.merchant = merchant
        holder.binding.rating.text = if (merchant.ratings.isNaN()) "No Reviews" else
            "${merchant.ratings} (${merchant.reviews.size} ${if (merchant.reviews.size == 1) "Review"
            else "Reviews"})"
        Glide
            .with(holder.itemView.context)
            .load(getImageUrl(merchant.image))
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.binding.merchantImg)

        if (currentUserEntity != null) {
            holder.binding.checkboxFavorite.visibility = View.VISIBLE
            holder.binding.checkboxFavorite.apply {
                this.setSafeOnClickListener {
                    setOnFavoriteClick(merchant, position, isChecked)
                }
            }
        } else {
            holder.binding.checkboxFavorite.visibility = View.GONE
        }

        holder.binding.cardView.setOnClickListener {
            onMerchantClicked?.invoke(merchant)
        }
    }

    fun setUser(userEntity: UserEntity?) {
        currentUserEntity = userEntity
    }

    private fun setOnFavoriteClick(data: Merchant, position: Int, isChecked: Boolean) {
            if (!isChecked) {
                onFavoriteClicked?.invoke(data, position, REMOVE_FROM_FAVORITES)
            } else {
                onFavoriteClicked?.invoke(data, position, ADD_TO_FAVORITES)
            }
    }
}

class MerchantViewHolder(val binding: ItemMerchantBinding): RecyclerView.ViewHolder(binding.root)