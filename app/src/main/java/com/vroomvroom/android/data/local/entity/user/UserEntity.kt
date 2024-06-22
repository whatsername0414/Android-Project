package com.vroomvroom.android.data.local.entity.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroomvroom.android.utils.Constants.LOCATION_TABLE
import com.vroomvroom.android.utils.Constants.USER_TABLE

@Entity(tableName = USER_TABLE)
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String? = null,
    val email: String? = null,
    @Embedded
    val phone: PhoneEntity? = null
)

data class PhoneEntity(
    val number: String? = null,
    val verified: Boolean = false
)

@Entity(tableName = LOCATION_TABLE)
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val street: String? = null,
    val barangay: String? = null,
    val city: String? = null,
    val additionalInfo: String? = null,
    val latitude: Double,
    val longitude: Double,
    val currentUse: Boolean = true
)