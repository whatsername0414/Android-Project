package com.vroomvroom.android.data.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class OrderStatus(val value: String): Parcelable {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    ACCEPTED("Accepted"),
    PICKED_UP("Picked Up"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled"),
}