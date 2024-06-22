package com.vroomvroom.android.data.mapper

import com.vroomvroom.android.data.model.user.Phone
import com.vroomvroom.android.data.local.entity.user.PhoneEntity
import com.vroomvroom.android.data.model.user.User
import com.vroomvroom.android.data.model.user.Address
import com.vroomvroom.android.data.remote.response.AddressResponse
import com.vroomvroom.android.data.local.entity.user.AddressEntity
import com.vroomvroom.android.data.local.entity.user.UserEntity
import com.vroomvroom.android.data.remote.request.AddressRequest
import com.vroomvroom.android.data.remote.response.PhoneResponse
import com.vroomvroom.android.data.remote.response.UserResponse

fun UserResponse?.toUser(): User {
    return User(
    id = this?.id.orEmpty(),
    name = this?.name.orEmpty(),
    email = this?.email.orEmpty(),
    phone = this?.phone.toPhone(),
    )
}

fun PhoneResponse?.toPhone(): Phone {
    return Phone(
        number = this?.number.orEmpty(),
        verified = this?.verified ?: false,
    )
}

fun UserResponse.toUserEntity(): UserEntity {
    return UserEntity(
        id = id.orEmpty(),
        name = name.orEmpty(),
        email = email.orEmpty(),
        phone = phone.toPhoneEntity(),
    )
}

fun PhoneResponse?.toPhoneEntity(): PhoneEntity {
    return PhoneEntity(
        number = this?.number.orEmpty(),
        verified = this?.verified ?: false,
    )
}

fun AddressResponse?.toAddress(): Address {
    return Address(
        street = this?.street.orEmpty(),
        barangay = this?.barangay.orEmpty(),
        city = this?.city.orEmpty(),
        additionalInfo = this?.additionalInfo.orEmpty(),
        latitude = this?.latitude ?: 0.0,
        longitude = this?.longitude ?: 0.0,
    )
}

fun AddressEntity.toAddress(): Address {
    return Address(
        id = id,
        street = street.orEmpty(),
        barangay = barangay.orEmpty(),
        city = city.orEmpty(),
        additionalInfo = additionalInfo.orEmpty(),
        latitude = latitude,
        longitude = longitude,
        currentUse = currentUse
    )
}

fun Address.toAddressEntity(): AddressEntity {
    return AddressEntity(
        id = id,
        street = street,
        barangay = barangay,
        city = city,
        additionalInfo = additionalInfo,
        latitude = latitude,
        longitude = longitude,
        currentUse = currentUse
    )
}

fun Address.toAddressRequest(): AddressRequest {
    return AddressRequest(
        street = street.orEmpty(),
        barangay = barangay.orEmpty(),
        city = city.orEmpty(),
        additionalInfo = additionalInfo.orEmpty(),
        latitude = latitude,
        longitude = longitude
    )
}