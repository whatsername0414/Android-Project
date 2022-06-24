package com.vroomvroom.android.data.model.user

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroomvroom.android.utils.Constants.LOCATION_TABLE
import com.vroomvroom.android.utils.Constants.USER_TABLE
import kotlinx.parcelize.Parcelize

@Entity(tableName = USER_TABLE)
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String? = null,
    val email: String? = null,
    @Embedded
    val phone: Phone? = null
)

data class Phone(
    val number: String? = null,
    val verified: Boolean = false
)

@Parcelize
@Entity(tableName = LOCATION_TABLE)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val address: String? = null,
    val city: String? = null,
    val addInfo: String? = null,
    val latitude: Double,
    val longitude: Double,
    val currentUse: Boolean = true
): Parcelable