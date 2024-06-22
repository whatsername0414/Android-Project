package com.vroomvroom.android.data.model.user

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: Phone
)

data class Phone(
    val number: String,
    val verified: Boolean
)

@Parcelize
data class Address(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val street: String? = null,
    val barangay: String? = null,
    val city: String? = null,
    val additionalInfo: String? = null,
    val latitude: Double,
    val longitude: Double,
    val currentUse: Boolean = true
): Parcelable
